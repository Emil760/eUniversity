package com.riverburg.eUniversity.logging.service.impl;

import com.riverburg.eUniversity.logging.model.Log;
import com.riverburg.eUniversity.logging.service.LoggingService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class MongoLoggingServiceImpl implements LoggingService {

    private final MongoTemplate mongoTemplate;

    @Override
    public void log(String uri, String methodType, Object body, ResponseEntity<?> response) {
        Log log = Log.builder()
                .url(uri)
                .methodType(methodType)
                .request(body)
                .responseBody(response.getBody())
                .build();

        mongoTemplate.insert(log);
    }

    @Override
    public void logError(String uri, String methodType, Object body, Throwable ex) {
        Log log = Log.builder()
                .url(uri)
                .methodType(methodType)
                .request(body)
                .responseBody(ex.getMessage())
                .createdAt(new Date())
                .build();

        mongoTemplate.insert(log);
    }
}
