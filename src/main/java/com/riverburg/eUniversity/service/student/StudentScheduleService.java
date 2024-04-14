package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleDetailResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleResponse;

import java.util.List;
import java.util.UUID;

public interface StudentScheduleService {

    List<StudentScheduleResponse> getSchedules(UUID accountId, short semester);

    StudentScheduleDetailResponse getSchedulesDetails(int scheduleId);
}
