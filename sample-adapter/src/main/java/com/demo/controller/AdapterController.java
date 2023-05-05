package com.demo.controller;


import com.demo.constant.ResponseStatus;
import com.demo.dto.ActivityResult;
import com.demo.dto.AdapterResponse;
import com.demo.dto.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AdapterController {
    final JmsTemplate jmsTemplate;
    final ObjectMapper customObjectMapper;

    @JmsListener(destination = "blocking.REQUEST")
    public void receiveConsume(Message message) throws Exception {
        log.info("\nreceive from blocking.REQUEST: " + message.getPayload());
        var request = customObjectMapper.readValue(message.getPayload().toString(), AdapterResponse.class);
        var data = customObjectMapper.readValue(request.getJsonData(), TransactionRequest.class);
        var activityResult = ActivityResult.builder()
                .responseCode(ResponseStatus.SUCCESS.getCode())
                .jsonData("Success: " + data.getTransactionId())
                .build();
        request.setJsonData(customObjectMapper.writeValueAsString(activityResult));
        var response = customObjectMapper.writeValueAsString(request);
        log.info("\nsend to blocking.RESPONSE: " + response);
        TimeUnit.SECONDS.sleep(2);
        jmsTemplate.convertAndSend("blocking.RESPONSE", response);
    }

}
