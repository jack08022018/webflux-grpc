package com.demo.service;

import com.demo.constant.AllFunction;
import com.demo.dto.ActivityResult;
import com.demo.utils.WorkflowUtils;
import com.demo.workflow.BlockingWorkflow;
import com.demo.workflow.TestWorkflow;
import grpc.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    final WebClient webClient;
    final WorkflowUtils workflowUtils;

    @Override
    public Mono<ActivityResult> nonBlocking() throws Exception {
        var workflowOptions = workflowUtils.getWorkflowOptions(AllFunction.TEST);
        var workflow = workflowUtils.buildWorkflow(TestWorkflow.class, workflowOptions);
        return workflow.nonBlocking();
    }

    @Override
    public ActivityResult blocking(TransactionRequest dto) throws Exception {
        var workflowOptions = workflowUtils.getWorkflowOptions(AllFunction.BLOCKING);
        var workflow = workflowUtils.buildWorkflow(BlockingWorkflow.class, workflowOptions);
        return workflow.blocking(dto);
    }
}

