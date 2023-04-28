package com.demo.adapter;


import grpc.ReceiveGrpcRequest;
import grpc.ReceiveGrpcResponse;

public interface MainAdapter {
    ReceiveGrpcResponse deduct(ReceiveGrpcRequest dto);
}
