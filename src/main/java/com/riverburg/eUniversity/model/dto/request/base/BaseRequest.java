package com.riverburg.eUniversity.model.dto.request.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class BaseRequest<TData> {

    private TData data;

    private final Date date = new Date();

}