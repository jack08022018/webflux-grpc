package com.demo.repository.mssql;

import com.demo.repository.mariadb.entity.ActorEntity;
import com.demo.repository.mssql.entity.RentalNewEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Qualifier("mssqlDatabaseClient")
public interface RentalNewRepository extends ReactiveCrudRepository<RentalNewEntity, Integer> {
    @Query("SELECT * FROM rental_new WHERE rental_id = :id")
    Mono<RentalNewEntity> findById(@Param("id") Integer id);
}
