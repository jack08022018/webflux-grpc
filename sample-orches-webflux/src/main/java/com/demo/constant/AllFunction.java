package com.demo.constant;

import java.util.Arrays;

public enum AllFunction {
    TEST("test"),
    BLOCKING("blocking");

    private String functionName;

    AllFunction(String functionName) {
        this.functionName = functionName;
    }

    public static AllFunction getEnum(String name) throws Exception {
        return Arrays.stream(values())
                .filter(s -> s.functionName.equals(name))
                .findFirst()
                .orElseThrow(() -> new Exception("Function is not defined"));
    }

    public String getFunctionName() {
        return functionName;
    }
}
