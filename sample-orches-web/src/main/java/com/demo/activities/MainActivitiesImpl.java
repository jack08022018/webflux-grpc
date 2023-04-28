package com.demo.activities;

import com.demo.adapter.MainAdapter;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import com.demo.utils.CommonUtils;
import com.google.gson.Gson;
import io.temporal.activity.Activity;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.client.ActivityCompletionClient;
import lombok.extern.slf4j.Slf4j;

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
    public ActivityResult getData(ActivityRequest dto) {
        ActivityExecutionContext context = Activity.getExecutionContext();
        var jsonData = gson.toJson(dto);
        var grpcRequest = CommonUtils.buildGrpcRequest(dto,context, jsonData);
        var grpcResponse = mainAdapter.deduct(grpcRequest);
        return CommonUtils.handleActivity(context, grpcResponse);
    }

}
