package com.demo.service;

import com.demo.dto.ActorDto;
import com.demo.dto.ResultDto;
import com.demo.entity.ClassEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ActorService {
    Mono<ResultDto> getData();
    Mono<ResultDto> saveData();
    Mono<Void> saveDataBatch();
    Mono<List<ActorDto>> getJoin();

    Mono<ResultDto> getDataZip();
}
