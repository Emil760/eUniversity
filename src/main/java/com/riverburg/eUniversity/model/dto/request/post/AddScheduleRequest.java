package com.riverburg.eUniversity.model.dto.request.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class AddScheduleRequest {

    private int groupId;

    private int facultyDisciplineId;

    private int eduProcessId;

    private int teacherId;

    @JsonFormat(timezone = "GMT+04:00")
    private Date beginDate;

    @JsonFormat(timezone = "GMT+04:00")
    private Date endDate;

    private short type;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+04:00")
    private LocalTime from;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+04:00")
    private LocalTime to;

    private int roomId;
}
