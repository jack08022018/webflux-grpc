package com.demo.utils;

import com.demo.config.exception.CommonException;
import com.demo.constant.ResponseStatus;
import com.demo.dto.ResultDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final Gson gson;

    public Mono<ResultDto> handleApi(ExcuteApi excuteApi) {
        return excuteApi.apply()
                .onErrorResume(CommonException.class, e -> {
                    log.error("AAA CommonException: " + e.getMessage(), e);
                    return Mono.just(ResultDto.builder()
                            .responseStatus(ResponseStatus.ERROR.getCode())
                            .description(e.getMessage())
                            .build());
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("AAA Exception: " + e.getMessage(), e);
                    return Mono.just(ResultDto.builder()
                            .responseStatus(ResponseStatus.ERROR.getCode())
                            .description(e.getMessage())
                            .build());
                });
    }
}
