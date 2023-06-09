package com.demo.config;

import com.demo.common.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    final CommonUtils commonUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelMap> mainExceptionHandler(Exception e) {
        var result = new ModelMap();
        result.put("message", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result);
    }

}
