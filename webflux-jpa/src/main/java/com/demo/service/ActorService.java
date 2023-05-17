package com.demo.service;

import com.demo.dto.RequestDto;
import com.demo.dto.ResultDto;
import com.demo.entity.ClientInfoEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ActorService {
    Mono<ResultDto> getDataZip() throws InterruptedException;
    Mono<List<ClientInfoEntity>> getData() throws InterruptedException;
    Mono<ResultDto> getDataWaiting() throws InterruptedException;
    ResultDto callSaveData(RequestDto dto) throws Exception;
}
