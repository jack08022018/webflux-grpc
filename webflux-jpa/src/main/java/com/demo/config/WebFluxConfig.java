package com.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {
    @Bean("httpClient")
    public HttpClient getHttpClient() {
        return HttpClient.create()
                .tcpConfiguration(client -> client
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10)))
                );
    }

    @Bean("orchesWebfluxClient")
    public WebClient getWebClient(@Autowired HttpClient httpClient) {
        var connector = new ReactorClientHttpConnector(httpClient.wiretap(true));
        return WebClient.builder()
                .baseUrl("http://localhost:9498/orches")
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}