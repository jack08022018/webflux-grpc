package com.demo.repository;

import com.demo.entity.RentalNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Qualifier("mssqlDatabaseClient")
public interface RentalNewRepository extends JpaRepository<RentalNewEntity, Integer> {
}
