package com.demo.service;

import com.demo.dto.RequestDto;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    final WebClient webClient;

    @Override
    public UserData getInfo(RequestDto<UserDto> dto) throws Exception {
//        int a = 1/0;
        var response = webClient.post()
                .uri("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class);
//                .exchangeToMono(s -> s.bodyToMono(UserData.class));
//                .block();
        return response.block();
    }
}

