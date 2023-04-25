package com.demo.adapter;

import grpc.TransactionRequest;
import grpc.TransactionResponse;
import reactor.core.publisher.Mono;

public interface MainAdapter {
    Mono<TransactionResponse> deduct(TransactionRequest request);
    TransactionResponse blocking(TransactionRequest request);
}
