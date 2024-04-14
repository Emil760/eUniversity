package com.riverburg.eUniversity.model.dto.request.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {

    private String search = "";

    private Integer pageIndex = 0;

    private Integer pageSize = 10;

}