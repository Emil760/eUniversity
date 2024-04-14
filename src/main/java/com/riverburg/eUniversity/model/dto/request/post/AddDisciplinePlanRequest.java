package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class AddDisciplinePlanRequest {

    private int facultyDisciplineId;

    private int eduProcessId;

    @Min(value = 1, message = "Min number is 1")
    private short count;

    private short grade;
}
