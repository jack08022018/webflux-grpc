package com.demo.controller;


import com.demo.adapter.SenderAdapter;
import com.demo.service.SenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final ObjectMapper customObjectMapper;
    final SenderAdapter senderAdapter;

    @GetMapping("/callGrpc")
    public Mono<TransactionResponse> callGrpc() {
        var request = TransactionRequest.newBuilder()
                .setTransactionId("1111")
                .setAccountId("aaaa")
                .build();
        return senderAdapter.deduct(request);
    }

}
