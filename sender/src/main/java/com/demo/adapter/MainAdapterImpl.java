package com.demo.adapter;

import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;
import com.google.common.util.concurrent.ListenableFuture;
import grpc.ReactorReceiveServiceGrpc;
import grpc.ReceiveServiceGrpc;
import grpc.TransactionRequest;
import grpc.TransactionResponse;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class MainAdapterImpl implements MainAdapter {
    @GrpcClient("receive-service")
    private static ReactorReceiveServiceGrpc.ReactorReceiveServiceStub reactorStub;

    @GrpcClient("receive-service")
    private static ReceiveServiceGrpc.ReceiveServiceBlockingStub blockingStub;

    @GrpcClient("receive-service")
    private static ReceiveServiceGrpc.ReceiveServiceFutureStub futureStub;

    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(2))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .option(EpollChannelOption.TCP_KEEPCNT, 8);
    WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:9398")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Mono<TransactionResponse> deduct(String id) {
        var request = TransactionRequest.newBuilder()
                .setTransactionId("1111")
                .setAccountId(id)
                .build();
        System.out.println("VVV");
        return reactorStub.deduct(request);
    }

    @Override
    public Mono<ActivityResult> deductHttp(String id) {
        var dto = ActivityRequest.builder()
                .transactionId("1111")
                .accountId("id1")
                .build();
        System.out.println("START deductHttp");
        return webClient.post()
                .uri("/api/deduct")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ActivityResult.class);
    }

    @Override
    public TransactionResponse blocking(TransactionRequest request) {
        return blockingStub.deduct(request);
    }

    @Override
    public TransactionResponse future(TransactionRequest request) throws ExecutionException, InterruptedException, TimeoutException {
        ListenableFuture<TransactionResponse> responseFuture = futureStub.deduct(request);
        return responseFuture.get(10, TimeUnit.SECONDS);
    }

}
