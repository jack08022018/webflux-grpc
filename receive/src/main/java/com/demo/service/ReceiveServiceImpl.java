package com.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import grpc.ReceiveServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ReceiveServiceImpl extends ReceiveServiceGrpc.ReceiveServiceImplBase {
    final ObjectMapper customObjectMapper;
    final Gson gson;

    private StatusRuntimeException getException(String message) {
        return Status.INVALID_ARGUMENT
                .withDescription(message)
                .asRuntimeException(new Metadata());
    }

    @Override
    public void deduct(TransactionRequest dto, StreamObserver<TransactionResponse> responseObserver) {
        log.info("REQUEST: {}", gson.toJson(dto));
        TransactionResponse response = TransactionResponse.newBuilder()
                .setResult("success!")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}