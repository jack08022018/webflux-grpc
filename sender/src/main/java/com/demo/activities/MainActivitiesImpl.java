package com.demo.activities;

import com.demo.adapter.MainAdapter;
import com.google.gson.Gson;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.temporal.client.ActivityCompletionClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MainActivitiesImpl implements MainActivities {
    private final MainAdapter mainAdapter;
    private final ActivityCompletionClient completionClient;
    final Gson gson = new Gson();

    public MainActivitiesImpl(MainAdapter mainAdapter, ActivityCompletionClient completionClient) {
        this.mainAdapter = mainAdapter;
        this.completionClient = completionClient;
    }

    @Override
    public Mono<TransactionResponse> deduct(TransactionRequest request) {
        System.out.println("AAA");
        return mainAdapter.deduct(request);
//        ActivityExecutionContext context = Activity.getExecutionContext();
//        byte[] taskToken = context.getTaskToken();
//        context.doNotCompleteOnReturn();
//        ForkJoinPool.commonPool().execute(() -> composeGreetingAsync(taskToken, "greeting!"));
    }

    @Override
    public TransactionResponse blocking(TransactionRequest request) {
        return mainAdapter.blocking(request);
    }

}
