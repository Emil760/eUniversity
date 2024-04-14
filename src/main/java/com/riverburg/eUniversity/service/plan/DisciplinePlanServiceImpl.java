package com.riverburg.eUniversity.service.plan;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplinePlanRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;
import com.riverburg.eUniversity.model.entity.DisciplinePlanEntity;
import com.riverburg.eUniversity.repository.DisciplinePlanRepository;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DisciplinePlanServiceImpl implements DisciplinePlanService {

    private final DisciplinePlanRepository disciplinePlanRepository;

    private final FacultyDisciplineService facultyDisciplineService;

    private final EduProcessService eduProcessService;

    @Override
    public List<DisciplinePlanResponse> getAll(int facultyDisciplineId) {
        return disciplinePlanRepository.findAll(facultyDisciplineId);
    }

    @Override
    public void addPlan(AddDisciplinePlanRequest request) throws RestException {
        validatePlan(request);

        var eduProcess = eduProcessService.findById(request.getEduProcessId());

        var facultyDiscipline = facultyDisciplineService.findById(request.getFacultyDisciplineId());

        var disciplinePlan = DisciplinePlanEntity.builder()
                .facultyDisciplineEntity(facultyDiscipline)
                .eduProcessEntity(eduProcess)
                .count(request.getCount())
                .grade(request.getGrade())
                .build();

        disciplinePlanRepository.save(disciplinePlan);
    }

    private void validatePlan(AddDisciplinePlanRequest request) {
        disciplinePlanRepository.findByFacultyDisciplineAndDegree(request.getFacultyDisciplineId(), request.getEduProcessId())
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

}
