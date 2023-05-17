package com.demo.repository;

import com.demo.entity.mariadb.ClassEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClassRepository extends ReactiveCrudRepository<ClassEntity, Long> {

}
