package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.request.post.SendStudentGradeRequest;
import com.riverburg.eUniversity.model.dto.response.teacher.StudentThemeResponse;

import java.util.List;

public interface TeacherWorkService {

    List<StudentThemeResponse> getThemesOfStudent(int studentId, int facultyDisciplineId, int eduProcessId);

    void saveStudentMark(SendStudentGradeRequest request);
}
