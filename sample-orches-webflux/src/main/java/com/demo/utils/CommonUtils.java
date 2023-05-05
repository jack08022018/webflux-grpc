package com.demo.utils;

import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.activity.ManualActivityCompletionClient;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public static ActivityResult handleActivity(ActivityExecutionContext context, ReceiveGrpcResponse res) {
        var status = ResponseStatus.getEnum(res.getResponseCode());
//        ManualActivityCompletionClient client = context.useLocalManualCompletion();
//        client.fail();
//        context.doNotCompleteOnReturn();
        switch (status) {
            case PROGRESSING -> context.doNotCompleteOnReturn();
            case SUCCESS -> {
                return ActivityResult.builder()
                        .responseCode(ResponseStatus.SUCCESS.getCode())
                        .description(ResponseStatus.SUCCESS.getDetail())
                        .jsonData(res.getJsonData())
                        .build();
            }
            default -> {
                return ActivityResult.builder()
                        .responseCode(ResponseStatus.ERROR.getCode())
                        .description(res.getDescription())
                        .build();
            }
        }
        return null;
    }

    public static <T> ReceiveGrpcRequest buildGrpcRequest(ActivityExecutionContext context,
                                                          String jsonData) {
        var workflowId = context.getInfo().getWorkflowId();
        var activityId = context.getInfo().getActivityId();
        return ReceiveGrpcRequest.newBuilder()
                .setWorkflowId(workflowId)
                .setActivityId(activityId)
                .setJsonData(jsonData)
                .build();
    }

}
