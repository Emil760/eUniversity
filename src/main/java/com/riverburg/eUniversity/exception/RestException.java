package com.riverburg.eUniversity.exception;

import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final Integer statusCode;

    public RestException(String message, HttpStatus httpStatus, Integer statusCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
    }

    public static RestException of(ErrorConstant error) {
        return new RestException(error.getMessage(), error.getHttpStatus(), error.getStatusCode());
    }

    public static RestException of(ErrorConstant error, String message) {
        return new RestException(message, error.getHttpStatus(), error.getStatusCode());
    }
}
