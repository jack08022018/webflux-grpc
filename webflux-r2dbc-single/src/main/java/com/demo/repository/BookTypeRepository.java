package com.demo.repository;

import com.demo.entity.mssql.BookTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookTypeRepository extends ReactiveCrudRepository<BookTypeEntity, Long> {
    Flux<BookTypeEntity> findByDetail(String detail);

}
