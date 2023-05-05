package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class NonBlockingWorkflowImpl implements NonBlockingWorkflow {
    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(15))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(1)
                    .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class, activityOptions);

    @Override
    public ActivityResult getDataNonBlocking(TransactionRequest dto) {
        return mainActivities.getDataNonBlocking();
    }
}
