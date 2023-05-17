package com.demo.repository;

import com.demo.entity.oracle.ClientInfoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends ReactiveCrudRepository<ClientInfoEntity, Long> {

}
