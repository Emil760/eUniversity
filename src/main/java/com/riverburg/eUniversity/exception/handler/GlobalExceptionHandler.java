package com.riverburg.eUniversity.exception.handler;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<?> handleRestEx(RestException restException) {
        return ResponseEntity
                .status(restException.getHttpStatus())
                .body(BaseResponse
                        .builder()
                        .message(restException.getMessage())
                        .statusCode(restException.getStatusCode())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleEx(Exception exception) {
        return ResponseEntity
                .internalServerError()
                .body(BaseResponse
                        .builder()
                        .message("Server error")
                        .statusCode(500)
                        .build());
    }
}
