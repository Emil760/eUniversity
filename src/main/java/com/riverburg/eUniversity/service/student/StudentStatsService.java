package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileStatsResponse;
import com.riverburg.eUniversity.model.entity.StudentEntity;

public interface StudentStatsService {

    StudentProfileStatsResponse getStudentProfileStats(StudentEntity student);
}
