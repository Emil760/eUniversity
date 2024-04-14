package com.riverburg.eUniversity.logging.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "logs")
@Getter
@Setter
@Builder
public class Log {

    @Id
    private String id;

    private String url;

    private String methodType;

    private Object request;

    private Object responseBody;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdAt;
}
