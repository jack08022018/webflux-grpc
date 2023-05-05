package com.demo.workflow;

import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface NonBlockingWorkflow {
    @WorkflowMethod
    ActivityResult getDataNonBlocking(TransactionRequest dto);

}
