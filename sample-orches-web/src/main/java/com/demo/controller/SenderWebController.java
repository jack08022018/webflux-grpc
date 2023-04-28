package com.demo.controller;


import com.demo.adapter.MainAdapter;
import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import com.demo.service.SenderService;
import com.google.gson.Gson;
import grpc.ReceiveGrpcResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SenderWebController {
    final SenderService senderService;
    final Gson gson;
//    final ObjectMapper customObjectMapper;
    final MainAdapter mainAdapter;

//    @GetMapping("/callGrpc")
//    public ReceiveGrpcResponse callGrpc() {
//        return mainAdapter.deduct("id1");
//    }

    @GetMapping("/flowBlocking")
    public ActivityResult flowBlocking(@RequestBody ActivityRequest dto) throws Exception {
        return senderService.blocking(dto);
    }

}
