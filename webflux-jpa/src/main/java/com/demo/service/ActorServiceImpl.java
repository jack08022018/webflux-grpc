package com.demo.service;

import com.demo.constant.ResponseStatus;
import com.demo.dto.*;
import com.demo.entity.TestTableEntity;
import com.demo.repository.ActorRepository;
import com.demo.repository.CountryRepository;
import com.demo.repository.RentalNewRepository;
import com.demo.repository.TestTableRepository;
import com.demo.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;
    final RentalNewRepository rentalNewRepository;
    final CountryRepository countryRepository;
    final TestTableRepository testTableRepository;
    final CommonUtils commonUtils;
    final WebClient orchesWebfluxClient;

    @Override
    public Mono<ResultDto> getDataZip() throws InterruptedException {
        var request = TransactionRequest.builder()
                .accountId("123")
                .transactionId("abc")
                .build();
        Mono<ActivityResult> atomicResult = orchesWebfluxClient.post()
                .uri("/api/flowNonBlocking")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ActivityResult.class);
        Mono<List> database = Mono.just(testTableRepository.findAll());
        return Mono.zip(atomicResult, database)
                .map(tuple -> ResultDto.builder()
                        .responseStatus(ResponseStatus.SUCCESS.getCode())
                        .data(tuple.getT1())
                        .db(tuple.getT2())
                        .build());
    }

    @Override
    public Mono<ResultDto> getDataWaiting() throws InterruptedException {
        var request = TransactionRequest.builder()
                .accountId("123")
                .transactionId("abc")
                .build();
        return orchesWebfluxClient.post()
                .uri("/api/flowNonBlocking")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ActivityResult.class)
                .flatMap(atomic -> Mono.just(testTableRepository.findAll())
                        .map(db -> ResultDto.builder()
                                .data(atomic)
                                .db(db)
                                .build()));
    }

//        Optional<ActorEntity> response1 = actorRepository.findById(1);
//        Optional<CountryEntity> response2 = countryRepository.findById(1);
//        ModelMap result = new ModelMap();
//        result.put("actor", response1.get());
//        result.put("country", response2.get());
//        return Mono.just(result);

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

    @Override
    @Transactional
    public ResultDto callSaveData(RequestDto dto) throws Exception {
        var entity = TestTableEntity.builder()
                .message(commonUtils.localDateToString(LocalDateTime.now(), "HH:mm:ss"))
                .build();
        testTableRepository.save(entity);
        TimeUnit.SECONDS.sleep(1);
//        int a = 1/0;
        return ResultDto.builder()
                .responseStatus("00")
                .build();
    }

    @Transactional
//    @Transactional("mariadbTransactionManager")
    public CompletableFuture<Void> saveData() {
        var entity = TestTableEntity.builder()
                .message("2341")
                .build();
        return CompletableFuture.runAsync(() -> testTableRepository.save(entity));
//        int a = 1/0;
//        return Mono.just(1)
//                .doOnNext(s-> {
//                    var entity = TestTableEntity.builder()
//                            .message("xxx")
//                            .build();
//                    testTableRepository.save(entity);
//                    int a = 1/0;
//                })
////                .doOnNext(s -> {
////                    int a = 1/0;
////                })
//                .then();

//        var actor = actorRepository.findById(1).get();
//        var country = countryRepository.findById(1).get();
//        actor.setFirstName(actor.getFirstName() + " aa");
//        country.setCountry(country.getCountry() + " aa");
//        actorRepository.save(actor);
////        int a = 1/0;
//        countryRepository.save(country);
//        Mono.error(new Exception(""));
//        return Mono.just(1).then();
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
