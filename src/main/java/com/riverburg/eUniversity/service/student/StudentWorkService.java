package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.request.post.AddStudentHomeworkRequest;
import com.riverburg.eUniversity.model.dto.response.student.homework.StudentHomeworkResponse;
import com.riverburg.eUniversity.model.entity.StudentWorkEntity;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface StudentWorkService {

    List<StudentHomeworkResponse> getAllWorks(UUID accountId, int facultyDisciplineId, int eduProcessId);

    boolean uploadWork(UUID accountId, AddStudentHomeworkRequest request) throws IOException;

    ByteArrayResource download(int studentWorkId) throws IOException;

    StudentWorkEntity findById(int id);
}
