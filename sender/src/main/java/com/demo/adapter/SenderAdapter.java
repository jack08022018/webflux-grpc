package com.demo.adapter;

import grpc.TransactionRequest;
import grpc.TransactionResponse;
import reactor.core.publisher.Mono;

public interface SenderAdapter {
    Mono<TransactionResponse> deduct(TransactionRequest request);
}
