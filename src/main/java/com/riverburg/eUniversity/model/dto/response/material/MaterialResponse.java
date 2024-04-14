package com.riverburg.eUniversity.model.dto.response.material;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class MaterialResponse {

    private Integer id;

    private UUID accountId;

    private String description;

    private String fileName;

}

