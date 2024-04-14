package com.riverburg.eUniversity.model.dto.response.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@ToString
public class BaseResponse<TData> {

    private int statusCode;

    private String message;

    private final Date date = new Date();

    private TData data;

}
