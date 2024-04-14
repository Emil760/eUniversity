package com.riverburg.eUniversity.model.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TeacherScheduleDetailResponse {

    String disciplineName;

    String cabinet;

    String eduProcess;
}
