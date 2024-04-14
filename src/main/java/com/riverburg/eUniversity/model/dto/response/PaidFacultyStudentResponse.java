package com.riverburg.eUniversity.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaidFacultyStudentResponse {

    private String groupName;

    private String fullName;

    private int ball;

    private boolean isPaid;
}
