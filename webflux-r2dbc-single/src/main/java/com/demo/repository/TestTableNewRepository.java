package com.demo.repository;

import com.demo.entity.mariadb.TestTableNewEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TestTableNewRepository extends ReactiveCrudRepository<TestTableNewEntity, Long> {
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("SELECT * FROM test_table_new WHERE rental_id = :id")
    Mono<TestTableNewEntity> findById(@Param("id") Long id);

}
