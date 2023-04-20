package com.demo.adapter;

import grpc.ReactorReceiveServiceGrpc;
import grpc.ReceiveServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SenderAdapterImpl implements SenderAdapter {
//    @GrpcClient("receive-service")
//    private ReceiveServiceGrpc.ReceiveServiceBlockingStub receiveServiceBlockingStub;

    @GrpcClient("receive-service")
    private static ReactorReceiveServiceGrpc.ReactorReceiveServiceStub stub;

    @PostConstruct
    public static void setup() throws Exception {
//        var channel = ManagedChannelBuilder
//                .forAddress("localhost", 8001)
//                .usePlaintext()
//                .build();
//        stub = ReactorReceiveServiceGrpc.newReactorStub(channel);
    }

    @Override
    public Mono<TransactionResponse> deduct(TransactionRequest request) {
//        Input input = Input.newBuilder()
//                .setNumber(5)
//                .build();
//        serviceStub.findSquare(input)
//                .map(Output::getResult)
//                .as(StepVerifier::create)
//                .expectNext(25L)
//                .verifyComplete();
        return stub.deduct(request);
    }

}
