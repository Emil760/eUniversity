package com.riverburg.eUniversity.service.discipline;


import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateDisciplineRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplineResponse;
import com.riverburg.eUniversity.model.entity.DisciplineEntity;
import com.riverburg.eUniversity.repository.DisciplineRepository;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.TeacherDisciplineRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final TeacherDisciplineRepository teacherDisciplineRepository;

    private final DataMapper dataMapper;

    @Override
    public PaginatedListResponse<DisciplineResponse> getPaginatedDisciplineList(PaginationRequest pagination, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var pageDisciplines = disciplineRepository.findByNameOrShortNameContaining(pageable, pagination.getSearch(), active);

        var itemsCount = pageDisciplines.getTotalElements();

        var disciplines = pageDisciplines
                .getContent()
                .stream()
                .map(dataMapper::disciplineEntityToDisciplineResponse)
                .toList();

        return new PaginatedListResponse<DisciplineResponse>(disciplines, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getDisciplineListDDL() {
        return disciplineRepository.findAllIsActiveDDL();
    }

    @Override
    public List<DDLResponse<Integer>> getDisciplineListShortDDL() {
        return disciplineRepository.findAllIsActiveShortDDL();
    }

    @Override
    public void addDiscipline(AddDisciplineRequest request) throws RestException {
        validateAddRequest(request.getName(), request.getShortName());

        DisciplineEntity disciplineEntity = dataMapper.disciplineRequestToDisciplineEntity(request);

        disciplineRepository.save(disciplineEntity);
    }

    @Override
    public void updateDiscipline(UpdateDisciplineRequest request) throws RestException {
        validateUpdateRequest(request.getId(), request.getName(), request.getShortName());

        DisciplineEntity disciplineEntity = findById(request.getId());

        disciplineEntity.setName(request.getName());
        disciplineEntity.setShortName(request.getShortName());

        disciplineRepository.save(disciplineEntity);
    }

    @Override
    public void deleteDiscipline(int id) throws RestException {
        findById(id);

        if (!facultyDisciplineRepository.findByDisciplineId(id).isEmpty())
            throw RestException.of(ErrorConstant.DISCIPLINE_USED_BY_FACULTY);

        if (!teacherDisciplineRepository.findByDiscipline(id).isEmpty())
            throw RestException.of(ErrorConstant.DISCIPLINE_USED_BY_TEACHER);

        disciplineRepository.deleteById(id);
    }

    @Override
    public void activateDiscipline(int id, boolean isActive) {
        DisciplineEntity disciplineEntity = findById(id);

        disciplineEntity.setIsActive(isActive);

        disciplineRepository.save(disciplineEntity);
    }

    @Override
    public DisciplineEntity findActiveById(int id) {
        return disciplineRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ACTIVE_DISCIPLINE_NOT_FOUND);
                });
    }

    private DisciplineEntity findById(int id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.DISCIPLINE_NOT_FOUND);
                });
    }

    private void validateAddRequest(String name, String shortName) {
        disciplineRepository.findByNameOrShortName(name, shortName)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

    private void validateUpdateRequest(int id, String name, String shortName) {
        disciplineRepository.findByNameOrShortNameExceptCurr(id, name, shortName)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
