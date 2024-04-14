package com.riverburg.eUniversity.model.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentRequest extends RegistrationRequest {

    @Min(value = 1, message = "Min value is 1")
    private short ball;
}
