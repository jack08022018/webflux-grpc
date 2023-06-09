package com.demo.config;

import com.demo.config.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleRuntimeException(ServerRequest request, Exception e) {
//        ErrorResponse response = ErrorResponse.builder()
//                .timestamp(commonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"))
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message(e.getMessage())
////                .path(request.uri().getPath())
//                .build();
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(response);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleBookAPIException(Exception e){
        log.error(e.getMessage(), e);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message", e.getMessage());
        errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.ok(errorMap);
    }

}
