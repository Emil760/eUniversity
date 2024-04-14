package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

@Getter
@ToString
public class UpdateStudentRequest extends UpdateBaseUser {

    private int id;

    private int groupId;

    @Min(value = 1, message = "Min value is 1")
    private short ball;

}