package com.riverburg.eUniversity.model.dto.response.student;

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
public class StudentScheduleResponse {

    int scheduleId;

    String disciplineName;

    @JsonFormat(pattern = "dd:MM:yyyy", timezone = "GMT+04:00")
    Date beginDate;

    @JsonFormat(pattern = "dd:MM:yyyy", timezone = "GMT+04:00")
    Date endDate;

    short type;

    LocalTime from;

    LocalTime to;
}
