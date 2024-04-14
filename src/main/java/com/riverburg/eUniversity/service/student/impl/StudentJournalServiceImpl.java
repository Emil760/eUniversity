package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.constant.EduProcessConstant;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalDisciplineGradeResponse;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalDisciplineItemGradeResponse;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalTotalGradeResponse;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.model.entity.StudentWorkEntity;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.JournalRepository;
import com.riverburg.eUniversity.repository.StudentWorkRepository;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.repository.user.StudentRepository;
import com.riverburg.eUniversity.service.student.StudentJournalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentJournalServiceImpl implements StudentJournalService {

    private final StudentRepository studentRepository;

    private final JournalRepository journalRepository;

    private final StudentWorkRepository studentWorkRepository;

    private final ThemeRepository themeRepository;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    @Override
    public StudentJournalDisciplineGradeResponse getStudentJournalGrades(UUID accountId, int facultyDisciplineId) {
        var student = studentRepository.findByAccountEntityId(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        final var facultyDiscipline = facultyDisciplineRepository.findAllByGroupId(
                        Objects.requireNonNull(student.getGroupEntity()).getId())
                .stream()
                .filter(f -> f.getId().equals(facultyDisciplineId))
                .findAny().orElseThrow(() -> RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND));

        var items = journalRepository
                .getAllByStudentEntityAndScheduleEntityFacultyDisciplineEntityAndGradeIsNotNull(student, facultyDiscipline)
                .stream()
                .map(e -> StudentJournalDisciplineItemGradeResponse
                        .builder()
                        .grade(e.getGrade())
                        .date(e.getDate())
                        .eduProcessName(e.getScheduleEntity().getEduProcessEntity().getName())
                        .build()
                ).toList();

        return StudentJournalDisciplineGradeResponse
                .builder()
                .disciplineId(facultyDiscipline.getDisciplineEntity().getId())
                .disciplineName(facultyDiscipline.getDisciplineEntity().getName())
                .grades(items)
                .build();
    }

    @Override
    public List<Short> getAllGradesByDiscipline(UUID accountId, int facultyDisciplineId) {
        var student = studentRepository.findByAccountEntityId(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        return journalRepository.getAllByStudentEntityAndGradeIsNotNull(student)
                .stream()
                .filter(e -> e.getScheduleEntity().getFacultyDisciplineEntity().getId().equals(facultyDisciplineId))
                .map(JournalEntity::getGrade)
                .collect(Collectors.toList());
    }

    @Override
    public StudentJournalTotalGradeResponse getTotalGradesByDiscipline(UUID accountId, int facultyDisciplineId) {
        var student = studentRepository.findByAccountEntityId(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        final var facultyDiscipline = facultyDisciplineRepository.findAllByGroupId(
                        Objects.requireNonNull(student.getGroupEntity()).getId())
                .stream()
                .filter(f -> f.getId().equals(facultyDisciplineId))
                .findAny().orElseThrow(() -> RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND));

        return StudentJournalTotalGradeResponse
                .builder()
                .attendance(Map.of(
                        "yes", journalRepository.countByAttendanceIsTrueAndScheduleEntity_FacultyDisciplineEntity(facultyDiscipline),
                        "no", journalRepository.countByAttendanceIsFalseAndScheduleEntity_FacultyDisciplineEntity(facultyDiscipline)
                ))
                .personalWorks(studentWorkRepository
                        .findAllByStudentEntityIdAndThemeEntityIsIn(student.getId(), themeRepository
                                .findByGroupAndDisciplineAndEduProcess(facultyDiscipline.getId(),
                                        EduProcessConstant.HOMEWORK))
                        .stream()
                        .map(StudentWorkEntity::getGrade)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .seminars(journalRepository
                        .getAllByStudentEntityAndGradeIsNotNull(student)
                        .stream()
                        .map(JournalEntity::getGrade)
                        .collect(Collectors.toList()))
                .labs(studentWorkRepository
                        .findAllByStudentEntityIdAndThemeEntityIsIn(student.getId(),
                                themeRepository.findByGroupAndDisciplineAndEduProcess(facultyDiscipline.getId(),
                                        EduProcessConstant.LABORATORY))
                        .stream()
                        .map(StudentWorkEntity::getGrade)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .build();
    }
}
