package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.constant.EduProcessConstant;
import com.riverburg.eUniversity.model.dto.response.student.total.StudentPlanTotalResponse;
import com.riverburg.eUniversity.model.dto.response.student.total.StudentTotalResponse;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.JournalRepository;
import com.riverburg.eUniversity.repository.StudentWorkRepository;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.repository.user.StudentRepository;
import com.riverburg.eUniversity.service.plan.DisciplinePlanService;
import com.riverburg.eUniversity.service.student.StudentTotalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentTotalServiceImpl implements StudentTotalService {

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final JournalRepository journalRepository;

    private final ThemeRepository themeRepository;

    private final StudentWorkRepository studentWorkRepository;

    private final DisciplinePlanService disciplinePlanService;

    private final StudentRepository studentRepository;

    @Override
    public List<StudentTotalResponse> getTotals(UUID accountId) {
        var student = studentRepository.findByAccountEntityId(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        var facultyDisciplines = facultyDisciplineRepository.findAllByGroupId(student.getGroupEntity().getId());

        var response = facultyDisciplines
                .stream()
                .map(fd -> {
                    var plans = disciplinePlanService.getAll(fd.getId());

                    var planGradesList = plans.stream()
                            .map(p -> {
                                switch (p.getId()) {
                                    case EduProcessConstant.HOMEWORK, EduProcessConstant.LABORATORY -> {
                                        short received = 0;

                                        var themes = themeRepository
                                                .findByGroupAndDisciplineAndEduProcess(fd.getId(), p.getId());

                                        for (var theme : themes) {
                                            var sw = studentWorkRepository
                                                    .findByThemeEntityAndStudentEntity(theme, student);

                                            if (sw.isPresent())
                                                received += Objects.nonNull(sw.get().getGrade()) ? sw.get().getGrade() : 0;
                                        }

                                        return new StudentPlanTotalResponse(
                                                p.getId(),
                                                p.getEduProcessName(),
                                                p.getGrade(),
                                                received
                                        );
                                    }
                                    default -> {
                                        return new StudentPlanTotalResponse(
                                                p.getId(),
                                                p.getEduProcessName(),
                                                p.getGrade(),
                                                journalRepository
                                                        .getAllByStudentEntityAndGradeIsNotNull(student)
                                                        .stream()
                                                        .mapToInt(JournalEntity::getGrade)
                                                        .average()
                                                        .getAsDouble());
                                    }
                                }
                            })
                            .toList();

                    return StudentTotalResponse
                            .builder()
                            .disciplineId(fd.getDisciplineEntity().getId())
                            .disciplineName(fd.getDisciplineEntity().getName())
                            .totals(planGradesList)
                            .build();
                })
                .toList();

        return response;
    }
}
