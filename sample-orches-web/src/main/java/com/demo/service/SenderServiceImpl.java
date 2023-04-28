package com.demo.service;

import com.demo.constant.AllFunction;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import com.demo.utils.WorkflowUtils;
import com.demo.workflow.BlockingWorkflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    final WorkflowUtils workflowUtils;

    @Override
    public ActivityResult blocking(ActivityRequest dto) throws Exception {
        var workflowOptions = workflowUtils.getWorkflowOptions(AllFunction.BLOCKING);
        var workflow = workflowUtils.buildWorkflow(BlockingWorkflow.class, workflowOptions);
        return workflow.blocking(dto);
    }
}

