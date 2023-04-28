package com.demo.adapter;

import com.demo.dto.ActivityResult;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface MainAdapter {
    Mono<TransactionResponse> deduct(String id);
    Mono<ActivityResult> deductHttp(String id);
    TransactionResponse blocking(TransactionRequest request);
    TransactionResponse future(TransactionRequest request) throws ExecutionException, InterruptedException, TimeoutException;
}
