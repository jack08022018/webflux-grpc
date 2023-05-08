package com.demo.utils;

import com.demo.dto.ActivityResult;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ExcuteApi<T> {
    Mono<ActivityResult> apply(T t) throws Exception;
}
