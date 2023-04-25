package com.demo.adapter;

import grpc.ReactorReceiveServiceGrpc;
import grpc.ReceiveServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MainAdapterImpl implements MainAdapter {
    @GrpcClient("receive-service")
    private static ReactorReceiveServiceGrpc.ReactorReceiveServiceStub stub;

    @GrpcClient("receive-service")
    private static ReceiveServiceGrpc.ReceiveServiceBlockingStub blockingStub;

    @Override
    public Mono<TransactionResponse> deduct(TransactionRequest request) {
        return stub.deduct(request);
    }

    @Override
    public TransactionResponse blocking(TransactionRequest request) {
        return blockingStub.deduct(request);
    }

}
