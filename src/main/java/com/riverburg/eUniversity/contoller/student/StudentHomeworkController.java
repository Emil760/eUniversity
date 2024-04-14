package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.dto.request.post.AddStudentHomeworkRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.homework.StudentHomeworkResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.file.ThemeFileService;
import com.riverburg.eUniversity.service.student.StudentWorkService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/homeworks")
@AllArgsConstructor
public class StudentHomeworkController {

    private final StudentWorkService studentWorkService;

    private final ThemeFileService themeFileService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<StudentHomeworkResponse>>> getAllHomeworks(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                                                       @RequestParam("facultyDisciplineId") int facultyDisciplineId,
                                                                                       @RequestParam("eduProcessId") int eduProcessId) {
        var result = studentWorkService.getAllWorks(context.getAccountId(), facultyDisciplineId, eduProcessId);

        return ResponseEntity.ok(BaseResponse
                .<List<StudentHomeworkResponse>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
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

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<?>> downloadHomework(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                            @ModelAttribute AddStudentHomeworkRequest request) throws IOException {
        studentWorkService.uploadWork(context.getAccountId(), request);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Homework is uploaded")
                .statusCode(200)
                .build());
    }
}
