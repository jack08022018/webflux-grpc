package com.demo.service;

import com.demo.repository.mariadb.ActorRepository;
import com.demo.repository.mariadb.entity.ActorEntity;
import com.demo.repository.mssql.RentalNewRepository;
import com.demo.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.ui.ModelMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;
    final RentalNewRepository rentalNewRepository;
    final TransactionalOperator operator;
    final R2dbcEntityTemplate entityTemplate;

    @Override
    public Mono getData() {
        return actorRepository.findById(1);
//        return rentalNewRepository.findById(152);
//        Mono<ActorEntity> response1 = actorRepository.findById(1);
//        Mono<RentalNewEntity> response2 = rentalNewRepository.findById(152);
//        return Mono.zip(response1, response2)
//                .map(tuple -> {
//                    ModelMap result = new ModelMap();
//                    result.put("mariadb", tuple.getT1());
//                    result.put("mssql", tuple.getT2());
//                    return result;
//                });

//        return actorRepository.findById(1)
//                .doOnSuccess(s -> {
//                    System.out.println("SUCCESS");
//                })
//                .doOnError(s -> {
//                    try {
//                        throw s;
//                    } catch (Throwable e) {
//                        throw new RuntimeException(e);
//                    }
////                    s.getMessage();
//                });
//        actor.subscribe();
//        return actorRepository.findById(1);
    }

    @Override
    @Transactional("mssqlTransactionManager")
    public Mono<RentalNewEntity> saveData() {
        return rentalNewRepository.findById(152)
                .doOnNext(s -> {
                    s.setInventoryId(25L);
                    System.out.println("AAAA");
                })
//                .map(s -> {
//                    System.out.println("AAAA");
//                    s.setFirstName(s.getFirstName() + " aa");
//                    return s;
//                })
                .flatMap(rentalNewRepository::save);
//                .then();
    }
//                .subscribe();
//                .thenReturn(toEvent(request))
//                .flatMap(this.eventRepository::save)
//                .as(operator::transactional)
//        Mono<ActorEntity> actor = actorRepository.findById(1);
//        return transactionalOperator.execute(status -> {
//            actor.flatMap(s -> {
//                s.setFirstName(s.getFirstName() + " aa");
//                return mariadbEntityTemplate.update(actor);
//            })
//                    .then(response2.flatMap(rentalNew -> {
//                rentalNew.setAmount(100); // update the amount
//                return r2dbcEntityTemplate.update(rentalNew); // update the entity and return the updated entity
//            }))
//                    .then();
//            return Mono.just(1L);
//        }).count();

//        Mono<ActorEntity> actor = actorRepository.findById(1)
//                .flatMap(s -> {
//                    s.setFirstName(s.getFirstName() + " aa");
//                    return actorRepository.save(s);
//                });
//        actor.subscribe();
//        int a = 1/0;
//        Mono<RentalNewEntity> rental = rentalNewRepository.findById(152)
//                .flatMap(s -> {
//                    s.setInventoryId(s.getInventoryId() + 1);
//                    return rentalNewRepository.save(s);
//                });
//        return Mono.just(1L);
}
