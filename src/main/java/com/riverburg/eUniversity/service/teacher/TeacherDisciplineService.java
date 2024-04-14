package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineToTeacher;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplinesResponse;

import java.util.List;


public interface TeacherDisciplineService {

    void addDiscipline(AddDisciplineToTeacher request);

    List<TeacherDisciplineResponse> getDisciplines(Integer teacherId);

    PaginatedListResponse<TeacherDisciplinesResponse> getTeachersDisciplines(PaginationRequest pagination);

    void deleteDiscipline(int teacherId, int disciplineId);

    List<DDLResponse<Integer>> getDisciplinedTeachersDDL(int disciplineId);
}