package com.riverburg.eUniversity.model.dto.response.student.profile;

import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentProfileAttendanceResponse {

    private StudentScheduleResponse disciplineSchedule;

    private List<Date> attendances;
}
