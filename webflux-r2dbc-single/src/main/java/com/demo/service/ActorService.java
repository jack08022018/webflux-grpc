package com.demo.service;

import com.demo.dto.ActorDto;
import com.demo.dto.MovieRentalInterface;
import com.demo.dto.ResultDto;
import com.demo.entity.ActorEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono<ResultDto> getData();
    Mono<ResultDto> saveData();
    Flux<ActorDto> getJoin();
}
