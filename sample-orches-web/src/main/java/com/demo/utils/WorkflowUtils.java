package com.demo.utils;

import com.demo.config.properties.TemporalProperties;
import com.demo.constant.AllFunction;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkflowUtils {
    final WorkflowClient workflowClient;
    final TemporalProperties temporalProperties;

    public <T> T buildWorkflow(Class<T> workflowInterface, WorkflowOptions workflowOptions) {
        return (T) workflowClient.newWorkflowStub(
                workflowInterface,
                workflowOptions.toBuilder()
                        .setWorkflowId(UUID.randomUUID().toString())
                        .build());
    }

    public WorkflowOptions getWorkflowOptions(AllFunction allFunction) {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(allFunction.name())
                .setWorkflowExecutionTimeout(Duration.ofSeconds(temporalProperties.getWorkflowExecutionTimeout()))
                .setWorkflowTaskTimeout(Duration.ofSeconds(temporalProperties.getWorkflowTaskTimeoutConfig()))
                .setRetryOptions(RetryOptions.newBuilder()
                        .setMaximumAttempts(1)
                        .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                        .build())
                .build();
    }
}
