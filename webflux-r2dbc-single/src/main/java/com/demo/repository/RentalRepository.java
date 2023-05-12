package com.demo.repository;

import com.demo.dto.MovieRentalInterface;
import com.demo.entity.RentalEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.core.sql.LockOptions;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RentalRepository extends ReactiveCrudRepository<RentalEntity, Integer> {
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("SELECT * FROM rental WHERE rental_id = :id")
    Mono<RentalEntity> findById(@Param("id") Integer id);

    @Query("""
            SELECT C.title, A.rental_date
            FROM rental A
                INNER JOIN inventory B ON B.inventory_id = A.inventory_id
                INNER JOIN film C ON C.film_id = B.film_id
            WHERE C.title LIKE 'BLANKET BEVERLY'
            ORDER BY C.title
        """)
//    Mono<List<MovieRentalInterface>> getRentalMoviesProjection(@Param("title") String title);
    Flux<MovieRentalInterface> getRentalMoviesProjection();
}
