package com.riverburg.eUniversity.model.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TeacherTimeTabsResponse {

    private int scheduleId;

    private String time;
}
