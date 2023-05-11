package com.demo.repository;

import com.demo.dto.MovieRentalInterface;
import com.demo.entity.TestTableNewEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TestTableNewRepository extends ReactiveCrudRepository<TestTableNewEntity, Long> {
    @Query("SELECT * FROM test_table_new WHERE rental_id = :id")
    Mono<TestTableNewEntity> findById(@Param("id") Long id);

}
