package com.demo.service;

import com.demo.entity.RentalEntity;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono getData();
    Mono<Void> saveData();
}
