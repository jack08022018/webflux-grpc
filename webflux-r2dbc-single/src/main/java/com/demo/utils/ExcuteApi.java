package com.demo.utils;

import com.demo.dto.ResultDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ExcuteApi {
    Mono<ResultDto> apply();
}
