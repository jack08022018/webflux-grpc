package com.demo.activities;

import com.demo.dto.ActivityResult;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import reactor.core.publisher.Mono;

@ActivityInterface
public interface MainActivities {
    @ActivityMethod
    Mono<TransactionResponse> deduct();

    @ActivityMethod
    Mono<ActivityResult> deductHttp();

    @ActivityMethod
    TransactionResponse blocking(TransactionRequest request);

}
