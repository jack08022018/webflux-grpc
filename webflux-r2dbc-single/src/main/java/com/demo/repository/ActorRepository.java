package com.demo.repository;

import com.demo.dto.ActorDto;
import com.demo.entity.ActorEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ActorRepository extends ReactiveCrudRepository<ActorEntity, Long> {
//    @Query("SELECT * FROM actor WHERE id = :id")
//    Flux<ActorEntity> findById(@Param("id") Long id);

    @Query("SELECT actor_id, first_name FROM actor WHERE actor_id = :id")
    Mono<ActorEntity> findById(@Param("id") Integer id);

    @Query("""
            SELECT A.actor_id, A.first_name, B.film_id
            FROM actor A
            	INNER JOIN film_actor B
            		ON B.actor_id = A.actor_id
            WHERE A.actor_id = 1""")
    Flux<ActorDto> findJoin();

    @Query("""
            WITH
            COUNTRY_DATA AS (
                SELECT *
                FROM country
                WHERE country_id < 30
            ),
            CITY_DATA AS (
                SELECT *
                FROM city
                WHERE city_id < 100
            )
            SELECT A.country, b.city
            FROM COUNTRY_DATA A
                INNER JOIN CITY_DATA B
                    ON B.country_id = A.country_id""")
    Flux<ActorDto> findCTE();

}
