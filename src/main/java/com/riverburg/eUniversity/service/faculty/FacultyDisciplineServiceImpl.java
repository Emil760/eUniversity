package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineToFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.FacultyDisciplineResponse;
import com.riverburg.eUniversity.model.entity.DegreeEntity;
import com.riverburg.eUniversity.model.entity.DisciplineEntity;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import com.riverburg.eUniversity.model.entity.FacultyEntity;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.service.misc.DegreeService;
import com.riverburg.eUniversity.service.discipline.DisciplineService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyDisciplineServiceImpl implements FacultyDisciplineService {

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final DegreeService degreeService;

    private final FacultyService facultyService;

    private final DisciplineService disciplineService;

    @Override
    public PaginatedListResponse<FacultyDisciplineResponse> getDisciplines(PaginationRequest pagination, int degreeId, int facultyId, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize(), Sort.by(Sort.Direction.ASC, "semesterNumber"));

        var pageFaculties = facultyDisciplineRepository.getDisciplines(pageable, degreeId, facultyId, active, pagination.getSearch());

        var itemsCount = pageFaculties.getTotalElements();

        var faculties = pageFaculties.toList();

        return new PaginatedListResponse<FacultyDisciplineResponse>(faculties, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getActiveDisciplinesListDDL(int facultyId, int degreeId) {
        return facultyDisciplineRepository.getActiveDisciplinesDDL(facultyId, degreeId);
    }

    @Override
    public void addDiscipline(AddDisciplineToFacultyRequest request) throws RestException {
        FacultyEntity facultyEntity = facultyService.findActiveById(request.getFacultyId());

        validateDiscipline(request, facultyEntity);

        DisciplineEntity disciplineEntity = disciplineService.findActiveById(request.getDisciplineId());

        DegreeEntity degreeEntity = degreeService.findById(request.getDegreeId());

        FacultyDisciplineEntity facultyDisciplineEntity = FacultyDisciplineEntity
                .builder()
                .degreeEntity(degreeEntity)
                .facultyEntity(facultyEntity)
                .disciplineEntity(disciplineEntity)
                .semesterNumber(request.getSemesterNumber())
                .build();

        facultyDisciplineRepository.save(facultyDisciplineEntity);
    }

    @Override
    public void activateDiscipline(int id, boolean isActive) throws RestException {
        FacultyDisciplineEntity facultyDisciplineEntity = findById(id);

        facultyDisciplineEntity.setIsActive(isActive);

        facultyDisciplineRepository.save(facultyDisciplineEntity);
    }

    @Override
    public void removeDiscipline(int id) throws RestException {
        findById(id);

        facultyDisciplineRepository.deleteById(id);
    }


    @Override
    public FacultyDisciplineEntity findById(int id) {
        return facultyDisciplineRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND);
                });
    }

    @Override
    public FacultyDisciplineEntity findByFacultyIdAndDisciplineId(int facultyId, int disciplineId) {
        return facultyDisciplineRepository
                .findByFacultyEntity_IdAndDisciplineEntity_IdAndIsActive(facultyId, disciplineId, true)
                .orElseThrow(() -> RestException.of(ErrorConstant.NOT_FOUND, "Faculty or discipline not found"));
    }

    private void validateDiscipline(AddDisciplineToFacultyRequest request, FacultyEntity facultyEntity) {
        facultyDisciplineRepository.findByDegreeAndFacultyAndDiscipline(
                        request.getDegreeId(),
                        request.getFacultyId(),
                        request.getDisciplineId())
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });

        if (facultyEntity.getSemesterCount() < request.getSemesterNumber())
            throw RestException.of(ErrorConstant.DISCIPLINE_SEMESTER_NUMBER_EXCEEDED);
    }
}
