package com.demo.repository;

import com.demo.entity.mariadb.TestTableNewEntity;
import com.demo.entity.mariadb.TestTableOldEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TestTableOldRepository extends ReactiveCrudRepository<TestTableOldEntity, Long> {
    @Query("SELECT * FROM test_table WHERE rental_id = :id")
    Mono<TestTableNewEntity> findById(@Param("id") Integer id);
}
