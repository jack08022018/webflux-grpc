package com.demo.controller;


import com.demo.adapter.MainAdapter;
import com.demo.dto.ActivityResult;
import com.demo.service.SenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SenderController {
    final SenderService senderService;
    final Gson gson;
//    final ObjectMapper customObjectMapper;
    final MainAdapter mainAdapter;

    @GetMapping("/callGrpc")
    public Mono<TransactionResponse> callGrpc() {
        return mainAdapter.deduct("id1");
    }

    @GetMapping("/flowBlocking")
    public ActivityResult flowBlocking() throws Exception {
        var request = TransactionRequest.newBuilder()
                .setTransactionId("1111")
                .setAccountId("aaaa")
                .build();
        return senderService.blocking(request);
    }

    @GetMapping("/flowNonBlocking")
    public Mono<ActivityResult> flowNonBlocking() throws Exception {
        return senderService.nonBlocking();
    }

    @GetMapping("/grpcNonBlocking")
    public Mono<ModelMap> nonBlocking() throws Exception {
        var request1 = mainAdapter.deduct("id1");
        var request2 = mainAdapter.deduct("id2");
        return Mono.zip(request1, request2)
                .map(tuple -> {
                    var result = new ModelMap();
                    result.put("request1", tuple.getT1().getResult());
                    result.put("request2", tuple.getT2().getResult());
                    return result;
                });
    }

}
