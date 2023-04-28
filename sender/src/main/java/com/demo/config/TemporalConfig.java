package com.demo.config;

import com.demo.config.exception.NotRetryException;
import com.demo.config.properties.TemporalProperties;
import com.demo.utils.WorkflowUtils;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class TemporalConfig {
    final TemporalProperties temporalProperties;

    @Bean
    public WorkflowClient workflowClient() {
        var options = WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(temporalProperties.getServer())
                        .build();
        var service = WorkflowServiceStubs.newServiceStubs(options);
        return WorkflowClient.newInstance(service);

//        var service = WorkflowServiceStubs.newInstance(
//                WorkflowServiceStubsOptions.newBuilder()
//                        .setTarget(temporalProperties.getServer())
//                        .build());
//        var option = WorkflowClientOptions.newBuilder()
////                .setNamespace("default")
//                .setDataConverter(new MyCustomDataConverter())
//                .build();
//        return WorkflowClient.newInstance(service, option);
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
