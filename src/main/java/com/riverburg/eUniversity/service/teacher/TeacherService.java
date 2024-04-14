package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegisterTeacherRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateTeacherRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherResponse;
import com.riverburg.eUniversity.model.entity.TeacherEntity;

import java.util.List;
import java.util.UUID;

public interface TeacherService {

    void register(RegisterTeacherRequest request);

    PaginatedListResponse<TeacherResponse> getTeachers(PaginationRequest request, int active);

    List<DDLResponse<Integer>> getActiveTeachersDDL();

    void update(UpdateTeacherRequest request);

    List<DDLResponse<Integer>> getTeacherDisciplineSector(int disciplineId, int sectorId);

    TeacherEntity findById(int id);

    TeacherProfileInfoResponse getTeacherProfileInfo(UUID accountId);
}
