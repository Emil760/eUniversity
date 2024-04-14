package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.post.SendStudentGradeRequest;
import com.riverburg.eUniversity.model.dto.response.teacher.StudentThemeResponse;
import com.riverburg.eUniversity.repository.StudentWorkRepository;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.service.student.StudentWorkService;
import com.riverburg.eUniversity.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherWorkServiceImpl implements TeacherWorkService {

    private final StudentService studentService;

    private final StudentWorkService studentWorkService;

    private final StudentWorkRepository studentWorkRepository;

    private final ThemeRepository themeRepository;

    @Override
    public List<StudentThemeResponse> getThemesOfStudent(int studentId, int facultyDisciplineId, int eduProcessId) {
        var student = studentService.findById(studentId);

        return themeRepository.findByGroupAndDisciplineAndEduProcess(facultyDisciplineId, eduProcessId)
                .stream()
                .map(t -> {
                    var studentWork = studentWorkRepository.findByThemeEntityAndStudentEntity(t, student);

                    var studentThemeResponseItem = StudentThemeResponse
                            .builder()
                            .themeId(t.getId())
                            .themeName(t.getName())
                            .themeFileName(t.getFileEntity() == null ? "" : t.getFileEntity().getOriginalFileName())
                            .themeDescription(t.getDescription())
                            .from(t.getFrom())
                            .to(t.getTo())
                            .order(t.getOrder())
                            .build();

                    if (studentWork.isPresent()) {
                        studentThemeResponseItem.setStudentWorkId(studentWork.get().getId());
                        studentThemeResponseItem.setStudentFileId(studentWork.get().getFileEntity().getId());
                        studentThemeResponseItem.setStudentFileName(studentWork.get().getFileEntity().getOriginalFileName());
                        studentThemeResponseItem.setGrade(studentWork.get().getGrade());
                        studentThemeResponseItem.setFeedback(studentWork.get().getFeedback());
                    }

                    return studentThemeResponseItem;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void saveStudentMark(SendStudentGradeRequest request) {
        validateSaveStudentMark(request.getStudentWorkId());

        var studentWork = studentWorkService.findById(request.getStudentWorkId());

        studentWork.setFeedback(request.getFeedback());
        studentWork.setGrade(request.getMark());

        studentWorkRepository.save(studentWork);
    }

    private void validateSaveStudentMark(int studentWorkId) {
        var studentWork = studentWorkService.findById(studentWorkId);

        if (studentWork.getGrade() != null) {
            throw RestException.of(ErrorConstant.HOMEWORK_HAS_GRADE);
        }
    }
}
