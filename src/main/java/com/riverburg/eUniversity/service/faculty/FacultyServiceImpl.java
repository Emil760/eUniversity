package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddFacultyRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.FacultyResponse;
import com.riverburg.eUniversity.model.entity.FacultyEntity;
import com.riverburg.eUniversity.repository.FacultyRepository;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final GroupRepository groupRepository;

    private final DataMapper dataMapper;


    public PaginatedListResponse<FacultyResponse> getPaginatedFacultyList(PaginationRequest pagination, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var pageFaculties = facultyRepository.findByNameOrShortName(pageable, pagination.getSearch(), active);

        var itemsCount = pageFaculties.getTotalElements();

        var faculties = pageFaculties
                .getContent()
                .stream()
                .map(dataMapper::facultyEntityToFacultyResponse)
                .toList();

        return new PaginatedListResponse<FacultyResponse>(faculties, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getFacultyListDDL() {
        return facultyRepository.findAllIsActiveDDL();
    }

    @Override
    public List<DDLResponse<Integer>> getFacultyListShortDDL() {
        return facultyRepository.findAllIsActiveShortDDL();
    }

    @Override
    public Short getSemesterCount(int facultyId) {
        var faculty = findById(facultyId);

        return faculty.getSemesterCount();
    }

    public List<FacultyResponse> getAllIsActivated() {
        return facultyRepository
                .findAllByIsActive(true)
                .stream()
                .map(dataMapper::facultyEntityToFacultyResponse)
                .toList();
    }

    public void addFaculty(AddFacultyRequest request) throws RestException {
        validateAddFaculty(request.getName(), request.getShortName());

        FacultyEntity facultyEntity = dataMapper.facultyRequestToFacultyEntity(request);

        facultyRepository.save(facultyEntity);
    }

    public void updateFaculty(UpdateFacultyRequest request) throws RestException {
        validateUpdateFaculty(request.getId(), request.getName(), request.getShortName());

        FacultyEntity facultyEntity = findById(request.getId());

        facultyEntity.setName(request.getName());
        facultyEntity.setShortName(request.getShortName());
        facultyEntity.setSemesterCount(request.getSemesterCount());

        facultyRepository.save(facultyEntity);
    }

    public void deleteFaculty(int id) throws RestException {
        findById(id);

        if (!facultyDisciplineRepository.findByFacultyId(id).isEmpty())
            throw RestException.of(ErrorConstant.FACULTY_USED_BY_DISCIPLINE);

        if (!groupRepository.findByFaculty(id).isEmpty())
            throw RestException.of(ErrorConstant.FACULTY_USED_BY_GROUP);

        facultyRepository.deleteById(id);
    }

    public void activateFaculty(int id, boolean isActive) throws RestException {
        FacultyEntity facultyEntity = findById(id);

        facultyEntity.setIsActive(isActive);

        facultyRepository.save(facultyEntity);
    }

    @Override
    public FacultyEntity findActiveById(int id) {
        return facultyRepository.findByIdAndActive(id, true)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ACTIVE_FACULTY_NOT_FOUND);
                });
    }

    @Override
    public FacultyEntity findById(int id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.FACULTY_NOT_FOUND);
                });
    }

    private void validateAddFaculty(String name, String shortName) {
        facultyRepository.findByNameOrShortName(name, shortName)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

    private void validateUpdateFaculty(int id, String name, String shortName) {
        facultyRepository.findByNameOrShortNameExceptCurr(id, name, shortName)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
