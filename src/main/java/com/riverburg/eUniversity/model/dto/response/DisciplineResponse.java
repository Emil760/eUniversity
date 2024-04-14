package com.riverburg.eUniversity.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DisciplineResponse {

    private int id;

    private String name;

    private String shortName;

    private short year;

    @JsonProperty("isActive")
    private boolean isActive;
}