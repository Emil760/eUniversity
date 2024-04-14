package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.constant.EduProcessConstant;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileStatsResponse;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import com.riverburg.eUniversity.model.entity.StudentWorkEntity;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.JournalRepository;
import com.riverburg.eUniversity.repository.StudentWorkRepository;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.service.student.StudentStatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class StudentStatsServiceImpl implements StudentStatsService {

    private final ThemeRepository themeRepository;

    private final StudentWorkRepository studentWorkRepository;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final JournalRepository journalRepository;

    @Override
    public StudentProfileStatsResponse getStudentProfileStats(StudentEntity student) {

        var groupId = Optional.ofNullable(student.getGroupEntity())
                .orElseThrow(() -> RestException.of(ErrorConstant.GROUP_NOT_FOUND))
                .getId();

        var facultyDisciplines = facultyDisciplineRepository.findAllByGroupId(groupId);

        Map<String, Object> disciplineHWStats = this.getDisciplineHWStats(student, facultyDisciplines);

        int totalHWs = (int) disciplineHWStats.get("totalHWs");
        int totalPassedHWs = (int) disciplineHWStats.get("totalPassedHWs");
        double avgHWsMarks = (double) disciplineHWStats.get("avgHWsMarks");

        double avgJournalMarks = this.getAvgMarkByJournalGrades(student);

        return StudentProfileStatsResponse.builder()
                .homeworksCount(totalHWs)
                .homeworksDoneCount(totalPassedHWs)
                .averageHWsMark(avgHWsMarks)
                .averageJournalMark(avgJournalMarks)
                .build();
    }

    private Double getAvgMarkByJournalGrades(StudentEntity student) {
        var journalItems = journalRepository.getAllByStudentEntityAndGradeIsNotNull(student);

        if (journalItems.isEmpty()) return 0.0;

        return journalItems
                .stream()
                .mapToInt(JournalEntity::getGrade)
                .average()
                .getAsDouble();
    }

    private Map<String, Object> getDisciplineHWStats(StudentEntity student, List<FacultyDisciplineEntity> facultyDisciplineList) {
        int totalHWs = 0;
        int totalPassedHWs = 0;
        int sumHWsMarks = 0;

        for (var fd : facultyDisciplineList) {

            var disciplineHws = themeRepository
                    .findByGroupAndDisciplineAndEduProcess(fd.getId(), EduProcessConstant.HOMEWORK);

            var disciplineStudentHws = studentWorkRepository
                    .findAllByStudentEntityIdAndThemeEntityIsIn(student.getId(), disciplineHws)
                    .stream()
                    .filter(e -> Objects.nonNull(e.getGrade())).toList();

            totalHWs += disciplineHws.size();
            totalPassedHWs += disciplineStudentHws.size();

            sumHWsMarks += disciplineStudentHws
                    .stream()
                    .mapToDouble(StudentWorkEntity::getGrade)
                    .sum();
        }

        return Map.of(
                "totalHWs", totalHWs,
                "totalPassedHWs", totalPassedHWs,
                "avgHWsMarks", (double) (sumHWsMarks / (totalPassedHWs > 0 ? totalPassedHWs : 1))
        );
    }
}
