package com.riverburg.eUniversity.model.dto.response.student;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StudentScheduleDetailResponse {

    String teacherName;

    String disciplineName;

    String cabinet;

    String eduProcess;
}
