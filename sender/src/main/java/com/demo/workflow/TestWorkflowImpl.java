package com.demo.workflow;

import com.demo.activities.MainActivities;
import com.demo.dto.ActivityResult;
import grpc.TransactionRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class TestWorkflowImpl implements TestWorkflow {
    ActivityOptions activityOptions = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(3)
                    .setDoNotRetry("com.demo.config.exceptions.NotRetryException")
                    .build())
            .build();

    private final MainActivities mainActivities = Workflow.newActivityStub(MainActivities.class, activityOptions);

    @Override
    public Mono<ActivityResult> test(TransactionRequest dto) throws Exception {
        return mainActivities.deduct(dto)
                .map(s -> ActivityResult.builder()
                        .jsonData(s.getResult())
                        .build());
    }
}
