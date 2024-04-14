package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegisterStudentRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateStudentRequest;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileAttendanceResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileStatsResponse;
import com.riverburg.eUniversity.model.entity.StudentEntity;

import java.util.List;
import java.util.UUID;


public interface StudentService {

    void register(RegisterStudentRequest request);

    PaginatedListResponse<StudentResponse> getStudent(PaginationRequest pagination, int groupId, int active);

    StudentProfileInfoResponse getStudentProfileInfo(UUID accountId);

    StudentProfileStatsResponse getStudentProfileStatsInfo(UUID accountId);

    List<StudentProfileAttendanceResponse> getStudentProfileAttendance(UUID accountId, short semester);

    StudentInfoResponse getStudentInfo(UUID accountId);

    void updateStudent(UpdateStudentRequest request);

    StudentEntity findById(int id);

    StudentEntity findByAccountId(UUID accountId);

}
