package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddPaidFaculty;
import com.riverburg.eUniversity.model.dto.request.put.UpdatePaidFaculty;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyResponse;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyStudentResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.PaidFacultyEntity;
import com.riverburg.eUniversity.repository.PaidFacultyRepository;
import com.riverburg.eUniversity.repository.user.StudentRepository;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.misc.DegreeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaidFacultyServiceImpl implements PaidFacultyService {

    private final PaidFacultyRepository paidFacultyRepository;

    private final StudentRepository studentRepository;

    private final GroupService groupService;

    private final FacultyService facultyService;

    private final DegreeService degreeService;

    private final DataMapper dataMapper;

    @Override
    public PaginatedListResponse<PaidFacultyResponse> getPaginatedPaidFaculties(int year, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var paidFacultiesPage = paidFacultyRepository.findPaidFaculties(year, pageable, pagination.getSearch());

        var paidFaculties = paidFacultiesPage
                .getContent()
                .stream()
                .map(dataMapper::paidFacultyEntityToPaidFacultyResponse)
                .toList();

        var itemsCount = paidFacultiesPage.getTotalElements();

        return new PaginatedListResponse<PaidFacultyResponse>(paidFaculties, itemsCount);
    }

    @Override
    public List<PaidFacultyStudentResponse> getAllPaidStudents(short year, int facultyId, int degreeId) {

        var students = paidFacultyRepository.getAllPaidStudents(year, facultyId, degreeId);

        return students.stream()
                .map(dataMapper::studentEntityToPaidFacultyStudentResponse)
                .toList();
    }

    @Override
    public void addPaidFaculty(AddPaidFaculty request) {
        validateAddFaculty(request);

        var faculty = facultyService.findActiveById(request.getFacultyId());

        var degree = degreeService.findById(request.getDegreeId());

        PaidFacultyEntity paidFacultyEntity = PaidFacultyEntity
                .builder()
                .degreeEntity(degree)
                .facultyEntity(faculty)
                .year(request.getYear())
                .freeCount(request.getCount())
                .build();

        paidFacultyRepository.save(paidFacultyEntity);

        sortPaidStudents(
                paidFacultyEntity.getYear(),
                paidFacultyEntity.getFacultyEntity().getId(),
                paidFacultyEntity.getDegreeEntity().getId(),
                paidFacultyEntity.getFreeCount());
    }

    @Override
    public void editPaidFaculty(UpdatePaidFaculty request) {
        var paidFaculty = findById(request.getId());

        paidFaculty.setFreeCount(request.getCount());

        paidFacultyRepository.save(paidFaculty);

        sortPaidStudents(
                paidFaculty.getYear(),
                paidFaculty.getFacultyEntity().getId(),
                paidFaculty.getDegreeEntity().getId(),
                paidFaculty.getFreeCount());
    }

    @Override
    public void deletePaidFaculty(int id) {
        var paidFaculty = findById(id);

        sortPaidStudents(
                paidFaculty.getYear(),
                paidFaculty.getFacultyEntity().getId(),
                paidFaculty.getDegreeEntity().getId(),
                0);

        paidFacultyRepository.deleteById(id);
    }

    @Override
    public void sortPaidStudents(short year, int facultyId, int degreeId, int freeCount) {
        var students = groupService.getStudentsByFacultyAndDegreeAndYear(year, facultyId, degreeId);

        for (int i = 0; i < students.size(); i++) {
            students.get(i).setIsPaid(i >= freeCount);
            studentRepository.save(students.get(i));
        }
    }

    @Override
    public PaidFacultyEntity findByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId) {
        return paidFacultyRepository.findByFacultyAndDegreeAndYear(year, facultyId, degreeId)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.FACULTY_NOT_FOUND);
                });
    }

    @Override
    public PaidFacultyEntity findById(int id) {
        return paidFacultyRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.FACULTY_NOT_FOUND);
                });
    }

    private void validateAddFaculty(AddPaidFaculty request) {
        paidFacultyRepository.findByFacultyAndDegreeAndYear(request.getYear(), request.getFacultyId(), request.getDegreeId())
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
