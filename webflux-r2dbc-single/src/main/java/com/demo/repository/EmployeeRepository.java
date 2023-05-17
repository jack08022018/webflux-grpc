package com.demo.repository;

import com.demo.entity.oracle.EmployeeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, Long> {

}
