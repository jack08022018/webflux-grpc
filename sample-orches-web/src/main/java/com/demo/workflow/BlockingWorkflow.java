package com.demo.workflow;

import com.demo.dto.TransactionRequest;
import com.demo.dto.ActivityResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BlockingWorkflow {
    @WorkflowMethod
    ActivityResult blocking(TransactionRequest dto) throws Exception;

}
