package com.demo.config.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends Exception {
    private String errorCode;
    private String description;

    public CommonException(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }
}
