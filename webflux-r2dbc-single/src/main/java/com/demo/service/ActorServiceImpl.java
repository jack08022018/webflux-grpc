package com.demo.service;

import com.demo.config.exception.CommonException;
import com.demo.constant.ResponseStatus;
import com.demo.dto.ActorDto;
import com.demo.dto.ResultDto;
import com.demo.entity.ActorEntity;
import com.demo.entity.RentalEntity;
import com.demo.entity.TestTableOldEntity;
import com.demo.repository.ActorRepository;
import com.demo.repository.RentalRepository;
import com.demo.repository.TestTableNewRepository;
import com.demo.repository.TestTableOldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;
    final RentalRepository rentalRepository;
    final TestTableNewRepository testTableNewRepository;
    final TestTableOldRepository testTableOldRepository;
    final TransactionalOperator operator;
    final R2dbcEntityTemplate entityTemplate;
    final DatabaseClient databaseClient;

    @Override
    public Mono<ResultDto> getData() {
        Mono<List<ActorDto>> response1 = actorRepository.findCTE().collectList();
        Mono<RentalEntity> response2 = rentalRepository.findById(152);
        return Mono.zip(response1, response2)
                .map(tuple -> ResultDto.builder()
                        .data1(tuple.getT1())
                        .data2(tuple.getT2())
                        .build());
    }

    public Mono<Void> saveDatabaseClient() {
        return databaseClient
                .sql("insert into test_table(message) values(:message)")
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("rental_id").execute())
                .bind("message", "my first post")
                .fetch()
                .first()
                .then();
    }
    public Mono<Void> saveDataVoid() {
        return Mono.just(1)
                .doOnNext(s -> {
                })
                .then(Mono.defer(() -> Mono.empty()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<ResultDto> saveData() {
        var oldEntity = TestTableOldEntity.builder()
                .status("new")
                .build();
        return testTableNewRepository.findById(1L)
                .doOnNext(s -> s.setMessage("III"))
                .flatMap(testTableNewRepository::save)
                .flatMap(s -> testTableOldRepository.save(oldEntity))
                .handle((s, sink) -> {
                    if (s.getStatus().equals("new")) {
                        sink.error(new CommonException("common Exception"));
                    }
                })
//                .flatMap(s -> {
//                    if (s.getStatus().equals("new")) {
//                        Mono.error(new CommonException("common Exception"));
//                    }
//                    return Mono.just(s);
//                })
                .then(Mono.just(ResultDto.builder()
                        .responseStatus(ResponseStatus.SUCCESS.getCode())
                        .build()));
    }

    @Override
    public Mono<List<ActorDto>> getJoin() {
        return actorRepository.findCTE().collectList();
    }

}
