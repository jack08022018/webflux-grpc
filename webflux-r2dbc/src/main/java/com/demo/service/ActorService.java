package com.demo.service;

import com.demo.repository.mariadb.entity.ActorEntity;
import com.demo.repository.mssql.entity.RentalNewEntity;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono getData();
    Mono<RentalNewEntity> saveData();
}
