package com.demo.service;

import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityResult;
import com.demo.dto.ActorDto;
import com.demo.dto.ResultDto;
import com.demo.dto.TransactionRequest;
import com.demo.entity.ClassEntity;
import com.demo.entity.RentalEntity;
import com.demo.entity.TestTableOldEntity;
import com.demo.repository.*;
import com.demo.utils.CommonUtils;
import io.r2dbc.spi.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    final WebClient orchesWebfluxClient;
    final CommonUtils commonUtils;
    final ClassRepository classRepository;

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
                .doOnNext(s -> {
                    s.setMessage(RandomStringUtils.randomAlphanumeric(6));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(testTableNewRepository::save)
//                .flatMap(s -> testTableOldRepository.save(oldEntity))
                .handle((s, sink) -> {
//                    if (s.getStatus().equals("new")) {
//                        sink.error(new CommonException("common Exception"));
//                    }
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
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> saveDataBatch() {
        var data = new ArrayList<ClassEntity>();
        for(int i = 0; i < 10000; i++) {
            data.add(ClassEntity.builder()
                    .className("class " + i)
                    .build());
        }
        var query = "INSERT INTO  class (class_name) VALUES (?)";
        return databaseClient.inConnectionMany(connection -> {
            var statement = connection.createStatement(query)
                    .returnGeneratedValues("id", "class_name");
            for (int i = 0; i < data.size(); ) {
                var post = data.get(i);
                statement.bind(0, post.getClassName());
                if (++i < data.size()) {
                    statement.add();
                }
            }
//            return Flux.from(statement.execute())
//                    .flatMap(result -> (Flux<ClassEntity>) result.map((row, rowMetadata) -> {
//                        Long id = row.get("id", Long.class);
//                        String class_name = row.get("class_name", String.class);
//                        return ClassEntity.builder()
//                                .id(id)
//                                .className(class_name)
//                                .build();
//                    }));
            return Flux.from(statement.execute())
                    .flatMap(result -> (Flux<Integer>) result.map((row, rowMetadata) -> 1));
        }).then();
    }

    @Override
    public Mono<List<ActorDto>> getJoin() {
        return actorRepository.findCTE().collectList();
    }

    @Override
    public Mono<ResultDto> getDataZip() {
        var request = TransactionRequest.builder()
                .accountId("123")
                .transactionId("abc")
                .build();
        Mono<ActivityResult> atomicResult = orchesWebfluxClient.post()
                .uri("/api/flowNonBlocking")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ActivityResult.class);
//        Mono<ActivityResult> atomicResult = Mono.just(ActivityResult.builder()
//                        .responseCode("09")
//                        .build());
        Mono<List<ActorDto>> database = actorRepository.findCTE().collectList();
        return Mono.zip(atomicResult, database)
                .map(tuple -> ResultDto.builder()
                        .responseStatus(ResponseStatus.SUCCESS.getCode())
                        .data1(tuple.getT1())
                        .data2(tuple.getT2())
                        .build());
    }

}
