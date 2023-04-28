package com.demo.config.jms;

import com.demo.constant.AllFunction;
import com.demo.dto.AdapterResponse;
import com.demo.utils.CommonUtils;
import com.google.gson.Gson;
import grpc.SignalRequest;
import grpc.SignalServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdapterConsumers implements JmsListenerConfigurer {
    final CommonUtils commonUtils;
    final Gson gson;

    @GrpcClient("orchestrator")
    private SignalServiceGrpc.SignalServiceBlockingStub signalServiceBlockingStub;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        for (AllFunction s : AllFunction.values()) {
            var queueName = commonUtils.getQueueResponse(s);
            log.info("Init consumer Function={} QueueName={}", s.name(), queueName);
            var endpoint = new SimpleJmsListenerEndpoint();
            endpoint.setId(queueName);
            endpoint.setDestination(queueName);
            endpoint.setMessageListener(message ->
                    CompletableFuture.runAsync(() -> {
                        if (message instanceof ActiveMQTextMessage textMessage) {
                            handleMessageConsume(textMessage);
                        } else {
                        }
                        log.info(">>> Can not parse message {}", message);
                    }));
            registrar.registerEndpoint(endpoint);
        }
    }

    private void handleMessageConsume(ActiveMQTextMessage textMessage) {
        try {
            String stringResponse = textMessage.getText();
            var adapterResponse = gson.fromJson(stringResponse, AdapterResponse.class);
            log.info("Consumer queueMessage={}", stringResponse);
            var signalRequest = SignalRequest.newBuilder()
                    .setWorkflowId (adapterResponse.getWorkflowId())
                    .setActivityId(adapterResponse.getActivityId())
                    .setJsonData(adapterResponse.getJsonData())
                    .build();
            log.info("Consumer signalRequest={}", gson.toJson(signalRequest));
            signalServiceBlockingStub
                    .withDeadlineAfter(15, TimeUnit.SECONDS)
                    .signal(signalRequest);
        } catch (Exception e) {
            log.error("Consumer error: " + e.getMessage(), e);
        }
    }

}