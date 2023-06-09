package com.demo.service;

import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import reactor.core.publisher.Mono;

public interface SenderService {
    Mono<ActivityResult> nonBlocking(TransactionRequest dto);
}
