package com.riverburg.eUniversity.model.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTeacherRequest extends RegistrationRequest {

    private int academicDegreeId;
}
