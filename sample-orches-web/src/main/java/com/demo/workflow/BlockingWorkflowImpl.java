package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class BlockingWorkflowImpl implements BlockingWorkflow {
    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(1)
                    .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class, activityOptions);

    @Override
    public ActivityResult blocking(ActivityRequest dto) throws Exception {
        return mainActivities.getData(dto);
    }
}
