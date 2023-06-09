package com.demo.activities;

import com.demo.adapter.MainAdapter;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import com.demo.utils.CommonUtils;
import com.google.gson.Gson;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainActivitiesImpl implements MainActivities {
    private final MainAdapter mainAdapter;

    final Gson gson = new Gson();

    public MainActivitiesImpl(MainAdapter mainAdapter) {
        this.mainAdapter = mainAdapter;
    }

    @Override
    public ActivityResult getData(TransactionRequest dto) {
        ActivityExecutionContext context = Activity.getExecutionContext();
        var jsonData = gson.toJson(dto);
        var grpcRequest = CommonUtils.buildGrpcRequest(context, jsonData);
        var grpcResponse = mainAdapter.deduct(grpcRequest);
        return CommonUtils.handleActivity(context, grpcResponse);
    }

    @Override
    public ActivityResult getDataNonBlocking() {
        ActivityExecutionContext context = Activity.getExecutionContext();
        TransactionRequest dto = TransactionRequest.builder()
                .accountId("abc")
                .transactionId("123")
                .build();
        var jsonData = gson.toJson(dto);
        var grpcRequest = CommonUtils.buildGrpcRequest(context, jsonData);
        var grpcResponse = mainAdapter.deduct(grpcRequest);
        return CommonUtils.handleActivity(context, grpcResponse);
    }

}
