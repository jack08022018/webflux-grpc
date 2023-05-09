package com.demo.repository;

import com.demo.entity.RentalEntity;
import com.demo.entity.TestTableEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TestTableRepository extends ReactiveCrudRepository<TestTableEntity, Integer> {
    @Query("SELECT * FROM test_table WHERE rental_id = :id")
    Mono<TestTableEntity> findById(@Param("id") Integer id);
}
