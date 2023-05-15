package com.demo.repository;

import com.demo.dto.ActorDto;
import com.demo.entity.ActorEntity;
import com.demo.entity.BookTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookTypeRepository extends ReactiveCrudRepository<BookTypeEntity, Long> {

}
