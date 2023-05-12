package com.demo.repository;

import com.demo.entity.ClassEntity;
import com.demo.entity.TestTableNewEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClassRepository extends ReactiveCrudRepository<ClassEntity, Long> {

}
