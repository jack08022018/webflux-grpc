package com.demo.config;

import com.demo.activities.MainActivitiesImpl;
import com.demo.adapter.MainAdapter;
import com.demo.config.exception.NotRetryException;
import com.demo.config.properties.TemporalProperties;
import com.demo.constant.AllFunction;
import com.demo.utils.WorkflowUtils;
import com.demo.workflow.NonBlockingWorkflowImpl;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.client.ActivityCompletionClient;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfig {
    final TemporalProperties temporalProperties;
    final MainAdapter mainAdapter;

    @Bean
    public WorkflowClient workflowClient(@Autowired WorkerFactoryOptions defaultWorkerFactoryOptions,
                                         @Autowired WorkerOptions defaultWorkerOptions,
                                         @Autowired WorkflowImplementationOptions defaultWorkflowImplementationOptions) {
        var options = WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(temporalProperties.getServer())
                        .build();
        var service = WorkflowServiceStubs.newServiceStubs(options);
        var client = WorkflowClient.newInstance(service);
        var factory = WorkerFactory.newInstance(client, defaultWorkerFactoryOptions);

        setupWorker(new MainActivitiesImpl(mainAdapter), AllFunction.BLOCKING.name(),
                factory, defaultWorkerOptions, defaultWorkflowImplementationOptions, NonBlockingWorkflowImpl.class);

        factory.start();
        return client;
    }

    private void setupWorker(Object activity, String taskQueueName, WorkerFactory factory,
                             WorkerOptions workerOptions, WorkflowImplementationOptions workflowOptions,
                             Class<?> workflow) {
        var worker = factory.newWorker(taskQueueName, workerOptions);
        worker.registerWorkflowImplementationTypes(workflowOptions, workflow);
        worker.registerActivitiesImplementations(activity);
    }

    @Bean
    public WorkerFactoryOptions defaultWorkerFactoryOptions() {
        return WorkerFactoryOptions.newBuilder()
                .setMaxWorkflowThreadCount(600)
//                .setWorkflowHostLocalPollThreadCount(5)
                .build();
    }

    @Bean
    public WorkerOptions defaultWorkerOptions() {
        var options = temporalProperties.getWorker();
        return WorkerOptions.newBuilder()
                .setMaxConcurrentActivityTaskPollers(options.getMaxConcurrentActivityTaskPollers())
                .setMaxConcurrentWorkflowTaskPollers(options.getMaxConcurrentWorkflowTaskPollers())
                .setMaxConcurrentActivityExecutionSize(options.getMaxConcurrentActivityExecutionSize())
                .setMaxConcurrentWorkflowTaskExecutionSize(options.getMaxConcurrentWorkflowTaskExecutionSize())
                .setMaxConcurrentLocalActivityExecutionSize(options.getMaxConcurrentLocalActivityExecutionSize())
                .setMaxWorkerActivitiesPerSecond(options.getMaxWorkerActivitiesPerSecond())
                .setMaxTaskQueueActivitiesPerSecond(options.getMaxTaskQueueActivitiesPerSecond())
                .build();
    }

    @Bean
    public WorkflowImplementationOptions defaultWorkflowImplementationOptions() {
        var defaultActivityOptions = ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(15))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                        .setMaximumAttempts(50)
                        .build())
                .build();

        var defaultLocalActivityOptions = LocalActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(15))
                .setLocalRetryThreshold(Duration.ofSeconds(15))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setBackoffCoefficient(1)
                        .setInitialInterval(Duration.ofMillis(10))
                        .setMaximumAttempts(9999)
                        .build())
                .build();
        return WorkflowImplementationOptions.newBuilder()
                .setFailWorkflowExceptionTypes(NotRetryException.class)
//                .setActivityOptions()
                .setDefaultActivityOptions(defaultActivityOptions)
                .setDefaultLocalActivityOptions(defaultLocalActivityOptions)
                .build();
    }

}
