package com.riverburg.eUniversity.contoller.teacher;

import com.riverburg.eUniversity.model.dto.request.post.SendStudentGradeRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.StudentThemeResponse;
import com.riverburg.eUniversity.service.file.ThemeFileService;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import com.riverburg.eUniversity.service.student.StudentWorkService;
import com.riverburg.eUniversity.service.teacher.TeacherWorkService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("teacher-panel/works")
@AllArgsConstructor
public class TeacherWorksController {

    private final GroupService groupService;

    private final EduProcessService eduProcessService;

    private final TeacherWorkService teacherWorkService;

    private final StudentWorkService studentWorkService;

    private final ThemeFileService themeFileService;

    @GetMapping("/students-by-group-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getStudentsByGroupDDL(@RequestParam("groupId") int groupId) {
        var result = groupService.getStudentsByGroupDDL(groupId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .data(result)
                .build());
    }

    @GetMapping("/disciplines-by-group-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getFacultyDisciplinesByGroupDDL(@RequestParam("groupId") int groupId) {
        var result = groupService.getFacultyDisciplinesByGroupDDL(groupId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .data(result)
                .build());
    }

    @GetMapping("/edu-processes-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getEduProcessesDDL() {
        var result = eduProcessService.getEduProcessesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .data(result)
                .build());
    }

    @GetMapping("/themes-of-student")
    public ResponseEntity<BaseResponse<List<StudentThemeResponse>>> getThemesOfStudent(@RequestParam("studentId") int studentId,
                                                                                       @RequestParam("facultyDisciplineId") int facultyDisciplineId,
                                                                                       @RequestParam("eduProcessId") int eduProcessId) {
        var result = teacherWorkService.getThemesOfStudent(studentId, facultyDisciplineId, eduProcessId);

        return ResponseEntity.ok(BaseResponse
                .<List<StudentThemeResponse>>builder()
                .statusCode(200)
                .data(result)
                .build());
    }

    @PostMapping("/send-student-work-mark")
    public ResponseEntity<BaseResponse<?>> sendMarkForStudentWork(@RequestBody SendStudentGradeRequest request) {
        teacherWorkService.saveStudentMark(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .build());
    }

    @GetMapping("/theme-download")
    public ResponseEntity<?> getThemeFile(@RequestParam("themeId") int themeId) throws IOException {
        var byteArrayResource = themeFileService.downloadTheme(themeId);
        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @GetMapping("/students-work-download")
    public ResponseEntity<?> getStudentWorkFile(@RequestParam("studentWorkId") int studentWorkId) throws IOException {
        var byteArrayResource = studentWorkService.download(studentWorkId);
        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}
