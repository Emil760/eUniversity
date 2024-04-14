package com.riverburg.eUniversity.logging.service;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

public interface LoggingService {

    @Async
    void log(String uri, String methodType, Object body, ResponseEntity<?> response) throws InterruptedException;

    @Async
    void logError(String uri, String methodType, Object body, Throwable ex);

}
