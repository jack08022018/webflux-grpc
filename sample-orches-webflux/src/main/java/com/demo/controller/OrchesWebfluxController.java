package com.demo.controller;


import com.demo.adapter.MainAdapter;
import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import com.demo.service.SenderService;
import com.demo.utils.CommonUtils;
import com.demo.utils.ExcuteApi;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class OrchesWebfluxController {
    final SenderService senderService;
    final Gson gson;
//    final ObjectMapper customObjectMapper;
    final MainAdapter mainAdapter;
    final CommonUtils commonUtils;

//    @GetMapping("/flowBlocking")
//    public ActivityResult flowBlocking() throws Exception {
//        var request = TransactionRequest.newBuilder()
//                .setTransactionId("1111")
//                .setAccountId("aaaa")
//                .build();
//        return senderService.blocking(request);
//    }

    @PostMapping("/flowNonBlocking")
    public Mono<ActivityResult> flowNonBlocking(@RequestBody TransactionRequest dto) throws Exception {
//        ExcuteApi<TransactionRequest> excuteApi = (TransactionRequest d) -> {
//            return senderService.nonBlocking(d);
//        };
        ExcuteApi<TransactionRequest> excuteApi = senderService::nonBlocking;
        return commonUtils.handleApi(excuteApi, dto);
    }

//    @GetMapping("/grpcNonBlocking")
//    public Mono<ModelMap> nonBlocking() throws Exception {
//        var request1 = mainAdapter.deduct("id1");
//        var request2 = mainAdapter.deduct("id2");
//        return Mono.zip(request1, request2)
//                .map(tuple -> {
//                    var result = new ModelMap();
//                    result.put("request1", tuple.getT1().getResult());
//                    result.put("request2", tuple.getT2().getResult());
//                    return result;
//                });
//    }

}
