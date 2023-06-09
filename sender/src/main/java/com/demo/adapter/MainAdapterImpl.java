package com.demo.adapter;

import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import grpc.ReceiveServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainAdapterImpl implements MainAdapter {
    @GrpcClient("receive-service")
    private static ReceiveServiceGrpc.ReceiveServiceBlockingStub blockingStub;

    @GrpcClient("receive-service")
    private static ReceiveServiceGrpc.ReceiveServiceFutureStub futureStub;

    @Override
    public ReceiveGrpcResponse deduct(ReceiveGrpcRequest dto) {
        return blockingStub.deduct(dto);
    }

}
