package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class AddPaidFaculty {

    private int facultyId;

    private int degreeId;

    private short year;

    @Min(value = 1, message = "Min value is 1")
    private short count;
}
