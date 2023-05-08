package com.demo.service;

import com.demo.entity.ActorEntity;
import com.demo.entity.CountryEntity;
import com.demo.repository.ActorRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.RentalNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;
    final RentalNewRepository rentalNewRepository;
    final CountryRepository countryRepository;

    @Override
    public Mono getData() {
//        return Mono.just(actorRepository.findById(1));
        Optional<ActorEntity> response1 = actorRepository.findById(1);
        Optional<CountryEntity> response2 = countryRepository.findById(1);
        ModelMap result = new ModelMap();
        result.put("actor", response1.get());
        result.put("country", response2.get());
        return Mono.just(result);

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
    @Transactional
//    @Transactional("mariadbTransactionManager")
    public Mono<Void> saveData() {
        var actor = actorRepository.findById(1).get();
        var country = countryRepository.findById(1).get();
        actor.setFirstName(actor.getFirstName() + " aa");
        country.setCountry(country.getCountry() + " aa");
        actorRepository.save(actor);
//        int a = 1/0;
        countryRepository.save(country);
        Mono.error(new Exception(""));
        return Mono.just(1).then();
//        return actorRepository.findById(1).get()
//                .doOnNext(s -> s.setFirstName(s.getFirstName() + " aa"))
//                .flatMap(actorRepository::save)
////                .thenReturn(toEvent(request))
////                .flatMap(this.eventRepository::save)
//                .as(operator::transactional)
//                .then();
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
    }
}
