package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@ToString
public class AddDisciplineToFacultyRequest {

    private Integer degreeId;

    private Integer facultyId;

    private Integer disciplineId;

    @Min(value = 1, message = "Min value is 1")
    private Short semesterNumber;
}
