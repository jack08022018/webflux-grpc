package com.demo.service;

import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import grpc.SignalRequest;
import grpc.SignalResponse;
import grpc.SignalServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.temporal.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class SignalServiceImpl extends SignalServiceGrpc.SignalServiceImplBase {
    final WorkflowClient workflowClient;
    final ObjectMapper customObjectMapper;

    @Override
    public void signal(SignalRequest request, StreamObserver<SignalResponse> responseObserver) {
        var completionClient = workflowClient.newActivityCompletionClient();
        var response = new ActivityResult();
        log.info("SignalServiceImpl workflowId={} activityId={} jsonData={}", request.getWorkflowId(), request.getActivityId(), request.getJsonData());
        try {
            response = customObjectMapper.readValue(request.getJsonData(), ActivityResult.class);
        } catch (JsonProcessingException e) {
            log.error("error SignalServiceImpl message={}", e.getMessage(), e);
            response = ActivityResult.builder()
                    .responseCode (ResponseStatus.ERROR.getCode())
                    .description(e.getMessage())
                    .jsonData("")
                    .build();
        }
        completionClient.complete(request.getWorkflowId(), Optional.empty(), request.getActivityId(), response);
        responseObserver.onNext(SignalResponse.newBuilder().setResult("success").build());
        responseObserver.onCompleted();
    }
}
