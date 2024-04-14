package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateTeacherRequest extends UpdateBaseUser {

    private int id;

    private int academicDegreeId;
}
