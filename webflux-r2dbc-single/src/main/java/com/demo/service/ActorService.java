package com.demo.service;

import com.demo.dto.ActorDto;
import com.demo.dto.ResultDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ActorService {
    Mono<ResultDto> getData();
    Mono<ResultDto> saveData();
    Mono<List<ActorDto>> getJoin();
}
