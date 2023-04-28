package com.demo.service;

import com.demo.constant.ResponseStatus;
import com.demo.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import grpc.ReceiveServiceGrpc;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.concurrent.TimeUnit;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ReceiveServiceImpl extends ReceiveServiceGrpc.ReceiveServiceImplBase {
    final ObjectMapper customObjectMapper;
    final Gson gson;
    final CommonUtils commonUtils;

    private StatusRuntimeException getException(String message) {
        return Status.INVALID_ARGUMENT
                .withDescription(message)
                .asRuntimeException(new Metadata());
    }

    @Override
    public void deduct(ReceiveGrpcRequest dto, StreamObserver<ReceiveGrpcResponse> responseObserver) {
        log.info("REQUEST: {}", gson.toJson(dto));
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){}

        ReceiveGrpcResponse response;
        try {
//            var result = commonUtils.sendMessage(dto);
        }catch (Exception e) {

        }
//        ReceiveGrpcResponse response = ReceiveGrpcResponse.newBuilder()
//                .setResponseCode(ResponseStatus.PROGRESSING.getCode())
//                .setDescription("")
//                .build();
//        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}