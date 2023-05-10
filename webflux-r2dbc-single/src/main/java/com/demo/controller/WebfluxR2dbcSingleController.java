package com.demo.controller;


import com.demo.dto.RequestDto;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;
import com.demo.entity.RentalEntity;
import com.demo.service.ActorService;
import com.demo.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class WebfluxR2dbcSingleController {
    final ApiService apiService;
    final Gson gson;
    final ObjectMapper customObjectMapper;
    final ActorService actorService;
//    final WebClient webClient;

    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(10))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .tcpConfiguration(client -> client
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .doOnConnected(conn -> conn
                            .addHandlerLast(new ReadTimeoutHandler(30))
                            .addHandlerLast(new WriteTimeoutHandler(30)))
            );
    WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:9199/ibmmq-consumer")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @GetMapping("/zipCombine")
    public Mono<ModelMap> zipCombine(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        Mono<UserData> response1 = webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class);
        var dto2 = customObjectMapper.convertValue(dto.getData(), UserDto.class);
        dto2.setName("King");
        Mono<UserData> response2 = webClient.post()
                .uri("/api/test")
                .bodyValue(dto2)
                .retrieve()
                .bodyToMono(UserData.class);

        return Mono.zip(response1, response2)
                .map(tuple -> {
                    ModelMap result = new ModelMap();
                    result.put("result1", tuple.getT1());
                    result.put("result2", tuple.getT2());
                    return result;
                });
    }

    @GetMapping("/waiting")
    public Mono<ModelMap> waiting(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        Mono<UserData> response1 = webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class);

        return response1.flatMap(result1 -> {
            dto.getData().setName("King");
            Mono<UserData> response2 = webClient.post()
                    .uri("/api/test")
                    .bodyValue(dto.getData())
                    .retrieve()
                    .bodyToMono(UserData.class);
            return response2.map(result2 -> {
                ModelMap result = new ModelMap();
                result.put("result1", result1);
                result.put("result2", result2);
                return result;
            });
        });
    }

    @GetMapping("/flatMap")
    public String flatMap(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class)
                .flatMap(s -> {
                    System.out.println(LocalDateTime.now() + " RESPONSE1: " + s.getAddress());
                    return webClient.post()
                            .uri("/api/test")
                            .bodyValue(dto.getData())
                            .retrieve()
                            .bodyToMono(UserData.class);
                })
                .subscribe(s -> {
                    System.out.println(LocalDateTime.now() + " RESPONSE2: " + s.getAddress());
                });
        return "second";
    }

    @GetMapping("/webClient2")
    public Mono<UserData> webClient2(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        Mono<UserData> first = webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class);

        Mono<UserData> second = first.flatMap(s -> {
            dto.getData().setName("Nhung");
            int a = 1/0;
            return webClient.post()
                    .uri("/api/test")
                    .bodyValue(dto.getData())
                    .retrieve()
                    .bodyToMono(UserData.class);
        });
        second.subscribe();

        return second
                .map(s -> s)
                .onErrorResume(ex -> {
                    throw new RuntimeException(ex);
                });
    }

    @GetMapping("/getData")
    public Mono<Object> getData() {
        try {
            return actorService.getData();
        }catch (Exception e) {
            log.error("AAA: " + e.getMessage(), e);
            return Mono.just("ERROR");
        }
    }

    @PostMapping("/saveData")
    public Mono<Void> saveData() {
        try {
            log.error("BBB");
            return actorService.saveData();
        }catch (Exception e) {
            log.error("AAA: " + e.getMessage(), e);
            return Mono.just("ERROR").then();
        }
    }

}
