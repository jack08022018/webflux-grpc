package com.demo.service;

import reactor.core.publisher.Mono;

public interface ActorService {
    Mono getData();
    Mono<Void> saveData();
}
