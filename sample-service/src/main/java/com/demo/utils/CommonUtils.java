package com.demo.utils;

import com.demo.constant.AllFunction;
import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityResult;
import com.demo.dto.AdapterRequest;
import com.google.gson.Gson;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import io.temporal.activity.ActivityExecutionContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final Gson gson;
    final JmsTemplate jmsTemplate;

    public static ActivityResult handleActivity(ActivityExecutionContext context, ReceiveGrpcResponse res) {
        var status = ResponseStatus.getEnum(res.getResponseCode());
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

    public String getQueueRequest(AllFunction function) {
        return function.getFunctionName() + ".REQUEST";
    }
    public String getQueueResponse(AllFunction function) {
        return function.getFunctionName() + ".RESPONSE";
    }

    public void sendMessage(ReceiveGrpcRequest request, String queueName) {
        var adapterRequest = AdapterRequest.builder()
                .workflowId(request.getWorkflowId())
                .activityId(request.getActivityId())
                .jsonData(request.getJsonData())
                .build();
        var message = gson.toJson(adapterRequest);
        jmsTemplate.convertAndSend(queueName, message);
    }
}
