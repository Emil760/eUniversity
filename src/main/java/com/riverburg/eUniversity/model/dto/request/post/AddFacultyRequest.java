package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class AddFacultyRequest {

    @NotBlank(message = "Name can`t be empty")
    private String name;

    @NotBlank(message = "Short name can`t be empty")
    private String shortName;

    @Min(value = 1, message = "SemesterDates count")
    private Short semesterCount;
}
