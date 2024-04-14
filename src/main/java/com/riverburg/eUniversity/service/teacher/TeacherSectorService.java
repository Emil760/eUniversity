package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddSectorToTeacher;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorsResponse;

import java.util.List;

public interface TeacherSectorService {

    void addSector(AddSectorToTeacher request);

    List<TeacherSectorResponse> getSectors(Integer teacherId);

    PaginatedListResponse<TeacherSectorsResponse> getTeachersSectors(PaginationRequest pagination);

    void deleteSector(int teacherId, int sectorId);
}
