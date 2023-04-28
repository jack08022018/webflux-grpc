package com.demo.workflow;

import com.demo.dto.ActivityResult;
import grpc.TransactionRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import reactor.core.publisher.Mono;

@WorkflowInterface
public interface TestWorkflow {
    @WorkflowMethod
    Mono<ActivityResult> nonBlocking() throws Exception;

}
