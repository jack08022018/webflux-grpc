package com.demo.controller;

@FunctionalInterface
public interface ExcuteApi<T, R> {
    R apply(T t) throws Exception;
}
