package com.demo.controller;


import com.demo.adapter.MainAdapter;
import com.demo.dto.TransactionRequest;
import com.demo.dto.ActivityResult;
import com.demo.service.SenderService;
import com.google.gson.Gson;
import grpc.ReceiveGrpcResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrchestratorController {
    final SenderService senderService;
    final Gson gson;
//    final ObjectMapper customObjectMapper;
    final MainAdapter mainAdapter;

    @GetMapping("/callGrpc")
    public ReceiveGrpcResponse callGrpc() {
//        return mainAdapter.deduct("id1");
        return ReceiveGrpcResponse.newBuilder()
                .setResponseCode("00")
                .setDescription("Success!")
                .build();
    }

    @PostMapping("/flowBlocking")
    public ActivityResult flowBlocking(@RequestBody TransactionRequest dto) throws Exception {
        return senderService.blocking(dto);
    }

}
