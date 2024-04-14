package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.model.constant.ScheduleType;
import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineNextLessonResponse;
import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineResponse;
import com.riverburg.eUniversity.model.entity.*;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.ScheduleRepository;
import com.riverburg.eUniversity.service.student.StudentDisciplineService;
import com.riverburg.eUniversity.service.student.StudentService;
import com.riverburg.eUniversity.util.schedule.ScheduleUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentDisciplineServiceImpl implements StudentDisciplineService {

    private final StudentService studentService;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final ScheduleRepository scheduleRepository;

    private final ScheduleUtil scheduleUtil;

    @Override
    public List<StudentDisciplineResponse> findAllStudentDisciplines(UUID accountId) {
        var student = studentService.findByAccountId(accountId);
        return facultyDisciplineRepository.findAllByGroupId(Objects.requireNonNull(student.getGroupEntity()).getId())
                .stream()
                .map(fd -> StudentDisciplineResponse
                        .builder()
                        .disciplineId(fd.getDisciplineEntity().getId())
                        .disciplineName(fd.getDisciplineEntity().getName())
                        .semesterNumber(fd.getSemesterNumber())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDisciplineNextLessonResponse> getCalculatedNextDisciplinesLessons(UUID accountId) {
        var student = studentService.findByAccountId(accountId);
        var currentDate = LocalDateTime.now();
        return facultyDisciplineRepository.findAllByGroupId(Objects.requireNonNull(student.getGroupEntity()).getId())
                .stream()
                .filter(fd -> Objects.equals(fd.getSemesterNumber(), student.getGroupEntity().getSemester()))
                .map(fd -> {
                            var schedules = scheduleRepository
                                    .findAllByFacultyDisciplineEntityAndGroupEntity(fd, student.getGroupEntity())
                                    .stream()
                                    .peek(s -> s.setNextDate(scheduleUtil.findNextDate(
                                            currentDate,
                                            s.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(
                                                    s.getFrom().getHour(), s.getFrom().getMinute()),
                                            s.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(
                                                    s.getTo().getHour(), s.getTo().getMinute()),
                                            ScheduleType.fromShort(s.getType()))))
                                    .filter(s -> Objects.nonNull(s.getNextDate()))
                                    .toList();

                            return schedules.isEmpty() ? null : StudentDisciplineNextLessonResponse
                                    .builder()
                                    .disciplineId(fd.getDisciplineEntity().getId())
                                    .nextLessonDate(Collections.min(schedules, Comparator.comparing(ScheduleEntity::getNextDate))
                                            .getNextDate().toLocalDate())
                                    .build();
                        }
                )
                .filter(Objects::nonNull)
                .toList();

    }


}
