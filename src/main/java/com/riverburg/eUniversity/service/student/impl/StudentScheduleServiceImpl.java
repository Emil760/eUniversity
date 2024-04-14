package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleDetailResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleResponse;
import com.riverburg.eUniversity.repository.ScheduleRepository;
import com.riverburg.eUniversity.service.schedule.ScheduleService;
import com.riverburg.eUniversity.service.student.StudentScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentScheduleServiceImpl implements StudentScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleService scheduleService;

    private final DataMapper dataMapper;

    @Override
    public List<StudentScheduleResponse> getSchedules(UUID accountId, short semester) {
        var schedules = scheduleRepository.getStudentsSchedules(accountId, semester);

        return schedules
                .stream()
                .map(dataMapper::scheduleEntityToStudentScheduleResponse)
                .toList();
    }

    @Override
    public StudentScheduleDetailResponse getSchedulesDetails(int scheduleId) {
        var schedule = scheduleService.findById(scheduleId);

        return dataMapper.scheduleEntityToStudentScheduleDetailResponse(schedule);
    }
}
