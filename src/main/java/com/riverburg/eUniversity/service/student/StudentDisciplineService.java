package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineNextLessonResponse;
import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineResponse;

import java.util.List;
import java.util.UUID;

public interface StudentDisciplineService {

    List<StudentDisciplineNextLessonResponse> getCalculatedNextDisciplinesLessons(UUID accountId);

    List<StudentDisciplineResponse> findAllStudentDisciplines(UUID accountId);

}
