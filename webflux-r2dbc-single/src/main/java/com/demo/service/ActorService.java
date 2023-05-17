package com.demo.service;

import com.demo.dto.ActorDto;
import com.demo.dto.ResultDto;
import com.demo.entity.mssql.BookTypeEntity;
import com.demo.entity.oracle.ClientInfoEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface ActorService {
    Mono<ResultDto> getData();
    Flux<ClientInfoEntity> getBook();
    Mono<ResultDto> saveData();
    Mono<ResultDto> saveBook();
    Mono<ResultDto> saveOracle();
    Mono<Void> saveDataBatch();
    Mono<List<ActorDto>> getJoin();

    Mono<ResultDto> getDataZip();
    Flux<Map<String, Object>> excuteStore();
}
