package com.demo.repository;

import com.demo.entity.RentalEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RentalRepository extends ReactiveCrudRepository<RentalEntity, Integer> {
    @Query("SELECT * FROM rental WHERE rental_id = :id")
    Mono<RentalEntity> findById(@Param("id") Integer id);
}
