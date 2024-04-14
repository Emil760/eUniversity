package com.riverburg.eUniversity.service.faculty;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineToFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.FacultyDisciplineResponse;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;

import java.util.List;

public interface FacultyDisciplineService {
    PaginatedListResponse<FacultyDisciplineResponse> getDisciplines(PaginationRequest pagination, int degreeId, int facultyId, int active);

    List<DDLResponse<Integer>> getActiveDisciplinesListDDL(int facultyId, int degreeId);

    void addDiscipline(AddDisciplineToFacultyRequest request) throws RestException;

    void activateDiscipline(int id, boolean isActive) throws RestException;

    void removeDiscipline(int id) throws RestException;

    FacultyDisciplineEntity findById(int id) throws RestException;

    FacultyDisciplineEntity findByFacultyIdAndDisciplineId(int facultyId, int disciplineId);
}
