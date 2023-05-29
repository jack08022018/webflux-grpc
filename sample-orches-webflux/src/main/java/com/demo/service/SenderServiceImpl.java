package com.demo.service;

import com.demo.constant.AllFunction;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import com.demo.utils.WorkflowUtils;
import com.demo.workflow.NonBlockingWorkflow;
import io.temporal.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    final WorkflowUtils workflowUtils;

    @Override
    public Mono<ActivityResult> nonBlocking(TransactionRequest dto) {
        var workflowOptions = workflowUtils.getWorkflowOptions(AllFunction.BLOCKING);
        var workflow = workflowUtils.buildWorkflow(NonBlockingWorkflow.class, workflowOptions);
        return Mono.fromFuture(WorkflowClient.execute(() -> {
            try {
                var result = workflow.getDataNonBlocking(dto);
                return result;
            } catch (Exception ex) {
                throw new RuntimeException("Workflow execution failed", ex);
            }
        }));
    }
//        return Mono.fromFuture(WorkflowClient.execute(() -> workflow.getDataNonBlocking(dto)));
}

