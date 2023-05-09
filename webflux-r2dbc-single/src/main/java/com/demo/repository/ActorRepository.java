package com.demo.repository;

import com.demo.entity.ActorEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ActorRepository extends ReactiveCrudRepository<ActorEntity, Long> {
//    @Query("SELECT * FROM actor WHERE id = :id")
//    Flux<ActorEntity> findById(@Param("id") Long id);

    @Query("SELECT * FROM actor WHERE actor_id = :id")
    Mono<ActorEntity> findById(@Param("id") Integer id);

}
