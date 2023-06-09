package com.demo.controller;


import com.demo.adapter.MainAdapter;
import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrchestratorController {
    final MainAdapter mainAdapter;

    @GetMapping("/callGrpc")
    public String callGrpc() {
        var request = ReceiveGrpcRequest.newBuilder()
                .setJsonData("request")
                .build();
        return mainAdapter.deduct(request).getJsonData();
    }

}
