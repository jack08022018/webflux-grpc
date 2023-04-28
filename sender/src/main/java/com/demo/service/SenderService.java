package com.demo.service;

import com.demo.dto.ActivityResult;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;
import grpc.TransactionRequest;
import reactor.core.publisher.Mono;

public interface SenderService {
    Mono<ActivityResult> nonBlocking() throws Exception;
    ActivityResult blocking(TransactionRequest dto) throws Exception;
}
