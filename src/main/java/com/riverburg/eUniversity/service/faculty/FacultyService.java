package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddFacultyRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.FacultyResponse;
import com.riverburg.eUniversity.model.entity.FacultyEntity;

import java.util.List;

public interface FacultyService {

    PaginatedListResponse<FacultyResponse> getPaginatedFacultyList(PaginationRequest pagination, int active);

    List<DDLResponse<Integer>> getFacultyListDDL();

    List<DDLResponse<Integer>> getFacultyListShortDDL();

    Short getSemesterCount(int facultyId) throws RestException;

    void addFaculty(AddFacultyRequest request) throws RestException;

    void updateFaculty(UpdateFacultyRequest request) throws RestException;

    void deleteFaculty(int id) throws RestException;

    void activateFaculty(int id, boolean isActive);

    FacultyEntity findById(int id);

    FacultyEntity findActiveById(int id);
}
