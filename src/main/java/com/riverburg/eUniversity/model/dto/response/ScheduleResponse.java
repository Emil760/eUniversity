package com.riverburg.eUniversity.model.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ScheduleResponse {
    Integer id;

    Integer facultyDisciplineId;

    Integer disciplineId;

    String disciplineName;

    Integer teacherId;

    String teacherName;

    Boolean isTeacherActive;

    Integer eduProcessId;

    String eduProcessName;

    Short type;

    @JsonFormat(timezone = "GMT+04:00")
    Date beginDate;

    @JsonFormat(timezone = "GMT+04:00")
    Date endDate;

    LocalTime from;

    LocalTime to;

    Integer roomId;

    String roomName;

    Boolean isActive;
}
