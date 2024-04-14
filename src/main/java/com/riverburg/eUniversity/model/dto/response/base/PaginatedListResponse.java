package com.riverburg.eUniversity.model.dto.response.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedListResponse<T> {

    private List<T> items;

    private Long allItemsCount;
}

