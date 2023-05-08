package com.demo.repository;

import com.demo.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {
//    @Query("SELECT * FROM actor WHERE id = :id")
//    Flux<ActorEntity> findById(@Param("id") Long id);

}
