package com.example.adapter;

import com.sender.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SenderAdapterImpl implements SenderAdapter {
    @GrpcClient("sender-service")
    private SenderServiceGrpc.SenderServiceBlockingStub senderStub;

    @Override
    public TransactionResponse deduct(TransactionRequest request) {
        return senderStub.deduct(request);
    }

    @Override
    public HelloResponse hello(HelloRequest request) {
        log.info("Sender: " + request.getInput());
        return senderStub.hello(request);
    }
}
