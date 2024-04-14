package com.riverburg.eUniversity.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FacultyResponse {

    private int id;

    private String name;

    private String shortName;

    private short semesterCount;

    private short year;

    @JsonProperty("isActive")
    private boolean isActive;
}
