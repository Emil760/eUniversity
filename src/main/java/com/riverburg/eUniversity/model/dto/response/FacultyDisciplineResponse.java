package com.riverburg.eUniversity.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FacultyDisciplineResponse {
    private int id;

    private String disciplineName;

    private String disciplineShortName;

    private short semesterNumber;

    @JsonProperty("isActive")
    private boolean isActive;
}
