package com.demo.utils;

import com.demo.config.exception.CommonException;
import com.demo.constant.ResponseStatus;
import com.demo.controller.OrchesWebfluxController;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import com.google.gson.Gson;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import io.temporal.activity.ActivityExecutionContext;
import io.temporal.activity.ManualActivityCompletionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final Gson gson;

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

    public Mono<ActivityResult> handleApi(ExcuteApi excute, TransactionRequest dto) {
        log.info("REQUEST: {}", gson.toJson(dto));
        try {
            return excute.apply(dto);
        }catch (CommonException e) {
            var result = ActivityResult.builder()
                    .responseCode(e.getErrorCode())
                    .description(e.getDescription())
                    .build();
            return Mono.just(result);
        }catch (Exception e) {
            var result = ActivityResult.builder()
                    .responseCode(ResponseStatus.ERROR.getCode())
                    .description(e.getMessage())
                    .build();
            return Mono.just(result);
        }
    }

}
