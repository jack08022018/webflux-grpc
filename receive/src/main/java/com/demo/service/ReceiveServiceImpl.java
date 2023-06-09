package com.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import grpc.ReceiveServiceGrpc;
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

    @Override
    public void deduct(ReceiveGrpcRequest dto, StreamObserver<ReceiveGrpcResponse> responseObserver) {
        log.info("REQUEST: {}", gson.toJson(dto));
        var response = ReceiveGrpcResponse.newBuilder()
                .setResponseCode("00")
                .setDescription("Success!")
                .setJsonData("response")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}