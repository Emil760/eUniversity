package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddPaidFaculty;
import com.riverburg.eUniversity.model.dto.request.put.UpdatePaidFaculty;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyResponse;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyStudentResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.PaidFacultyEntity;

import java.util.List;

public interface PaidFacultyService {

    PaginatedListResponse<PaidFacultyResponse> getPaginatedPaidFaculties(int year, PaginationRequest pagination);

    List<PaidFacultyStudentResponse> getAllPaidStudents(short year, int facultyId, int degreeId);

    void addPaidFaculty(AddPaidFaculty request);

    void editPaidFaculty(UpdatePaidFaculty request);

    void deletePaidFaculty(int id);

    PaidFacultyEntity findById(int id);

    void sortPaidStudents(short year, int facultyId, int degreeId, int freeCount);

    PaidFacultyEntity findByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId);

}
