package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateMaterialRequest {

    private Integer id;

    private String description;

    private String originalFileName;
}
