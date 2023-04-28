package com.demo.controller;


import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReceiveController {
    @PostMapping("/deduct")
    public Mono<ActivityResult> deduct(@RequestBody ActivityRequest request) {
        return Mono.just(ActivityResult.builder()
                        .jsonData(request.getAccountId())
                .build());
    }

}
