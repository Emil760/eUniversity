package com.riverburg.eUniversity.service.teacher;


import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineToTeacher;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplinesResponse;
import com.riverburg.eUniversity.model.entity.TeachersDisciplineEntity;
import com.riverburg.eUniversity.repository.TeacherDisciplineRepository;
import com.riverburg.eUniversity.service.discipline.DisciplineService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TeacherDisciplineServiceImpl implements TeacherDisciplineService {

    private final TeacherDisciplineRepository teacherDisciplineRepository;

    private final TeacherService teacherService;

    private final DisciplineService disciplineService;

    private final DataMapper dataMapper;

    @Override
    public void addDiscipline(AddDisciplineToTeacher request) {
        validate(request.getTeacherId(), request.getDisciplineId());

        var teacher = teacherService.findById(request.getTeacherId());

        var discipline = disciplineService.findActiveById(request.getDisciplineId());

        TeachersDisciplineEntity entity = TeachersDisciplineEntity
                .builder()
                .teacherEntity(teacher)
                .disciplineEntity(discipline)
                .build();

        teacherDisciplineRepository.save(entity);
    }

    @Override
    public List<TeacherDisciplineResponse> getDisciplines(Integer teacherId) {
        return teacherDisciplineRepository.findDisciplines(teacherId);
    }

    @Override
    public PaginatedListResponse<TeacherDisciplinesResponse> getTeachersDisciplines(PaginationRequest pagination) {
        var teachers = teacherDisciplineRepository.findTeachersDisciplines(
                pagination.getPageIndex(),
                pagination.getPageSize(),
                pagination.getSearch());

        var itemsCount = teacherDisciplineRepository.getTeachersDisciplinesCount(pagination.getSearch());

        return new PaginatedListResponse<TeacherDisciplinesResponse>(teachers, itemsCount);
    }

    //TODO maybe add some validation
    @Override
    public void deleteDiscipline(int teacherId, int disciplineId) {
        var discipline = findByTeacherAndDiscipline(teacherId, disciplineId);

        teacherDisciplineRepository.delete(discipline);
    }

    @Override
    public List<DDLResponse<Integer>> getDisciplinedTeachersDDL(int disciplineId) {
        var a = teacherDisciplineRepository.findByDiscipline(disciplineId);

        return a
                .stream()
                .map(dataMapper::teachersDisciplineEntityToDDL)
                .toList();
    }

    private TeachersDisciplineEntity findByTeacherAndDiscipline(int teacherId, int disciplineId) {
        return teacherDisciplineRepository.findByTeacherAndDiscipline(teacherId, disciplineId)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND);
                });
    }

    private void validate(int teacherId, int disciplineId) {
        teacherDisciplineRepository.findByTeacherAndDiscipline(teacherId, disciplineId)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

}
