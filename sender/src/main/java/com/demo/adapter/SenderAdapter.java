package com.example.adapter;

import com.sender.HelloRequest;
import com.sender.HelloResponse;
import com.sender.TransactionRequest;
import com.sender.TransactionResponse;

public interface SenderAdapter {
    TransactionResponse deduct(TransactionRequest request);
    HelloResponse hello(HelloRequest request);
}
