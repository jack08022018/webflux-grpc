package com.demo.workflow;

import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BlockingWorkflow {
    @WorkflowMethod
    ActivityResult blocking(ActivityRequest dto) throws Exception;

}
