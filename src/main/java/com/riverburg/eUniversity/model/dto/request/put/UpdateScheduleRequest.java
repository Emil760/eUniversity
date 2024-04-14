package com.riverburg.eUniversity.model.dto.request.put;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class UpdateScheduleRequest {

    private int id;

    private int eduProcessId;

    private int teacherId;

    private short day;

    private short type;

    @JsonFormat(timezone = "GMT+04:00")
    private Date beginDate;

    @JsonFormat(timezone = "GMT+04:00")
    private Date endDate;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+04:00")
    private LocalTime from;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+04:00")
    private LocalTime to;

    private int roomId;
}
