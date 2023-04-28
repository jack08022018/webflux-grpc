package com.demo.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AdapterController {
    final JmsTemplate jmsTemplate;

    @JmsListener(destination = "blocking.REQUEST")
    public void receiveConsume(Message message) throws JMSException {
        log.info("\nreceive from blocking.REQUEST: "+ message.getPayload());
    }

}
