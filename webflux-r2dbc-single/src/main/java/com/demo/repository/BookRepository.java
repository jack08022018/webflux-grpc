package com.demo.repository;

import com.demo.entity.ActorEntity;
import com.demo.entity.BookEntity;
import io.r2dbc.spi.Parameter;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<BookEntity, Integer> {
    @Query("SELECT * FROM book WHERE id = :id")
    Mono<BookEntity> findById(@Param("id") Integer id);
}
