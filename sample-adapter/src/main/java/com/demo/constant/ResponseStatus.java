package com.demo.constant;

import java.util.Arrays;

public enum ResponseStatus {
    ENUM_NOT_FOUND("", "ResCode is not found"),
    SUCCESS("00", "Success"),
    ERROR("06", ""),
    PROGRESSING("09", "Request in progress");

    private String code;
    private String detail;


    ResponseStatus(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public static ResponseStatus getEnum(String code) {
        return Arrays.stream(values())
                .filter(s -> s.code.equals(code))
                .findFirst()
                .orElseGet(() -> {
                    var status = ResponseStatus.ENUM_NOT_FOUND;
                    status.code = code;
                    return status;
                });

    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }
}
