package com.riverburg.eUniversity.model.dto.response.student.discipline;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentDisciplineResponse {

    private Integer disciplineId;

    private String disciplineName;

    private Short semesterNumber;
}
