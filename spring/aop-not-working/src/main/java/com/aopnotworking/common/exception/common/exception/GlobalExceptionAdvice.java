package com.aopnotworking.common.exception.common.exception;

import com.aopnotworking.common.exception.common.alert.SlackLogger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @SlackLogger
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(50000, e.getMessage()));
    }

    @Override
    @SlackLogger // 동작하지 않음.
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest().body(new ErrorResponse(51000, "Internal Server Error"));
    }
}


