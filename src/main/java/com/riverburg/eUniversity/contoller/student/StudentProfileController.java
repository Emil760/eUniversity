package com.riverburg.eUniversity.contoller.student;


import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileAttendanceResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileStatsResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/profile")
@AllArgsConstructor
public class StudentProfileController {

    private StudentService studentService;

    @GetMapping()
    public ResponseEntity<BaseResponse<StudentProfileInfoResponse>> getStudentProfileInfo(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = studentService.getStudentProfileInfo(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<StudentProfileInfoResponse>builder()
                .statusCode(200)
                .message("Student`s profile info returned")
                .data(response)
                .build());
    }

    @GetMapping("/attendance/{semester}")
    public ResponseEntity<BaseResponse<List<StudentProfileAttendanceResponse>>> getStudentAttendances(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                                                                      @PathVariable("semester") short semester) {
        var response = studentService.getStudentProfileAttendance(context.getAccountId(), semester);

        return ResponseEntity.ok(BaseResponse
                .<List<StudentProfileAttendanceResponse>>builder()
                .statusCode(200)
                .data(response)
                .build());
    }

    @GetMapping("/stats")
    public ResponseEntity<BaseResponse<StudentProfileStatsResponse>> getStudentProfileStatsInfo(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = studentService.getStudentProfileStatsInfo(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<StudentProfileStatsResponse>builder()
                .statusCode(200)
                .data(response)
                .build());
    }

    @GetMapping("info")
    public ResponseEntity<BaseResponse<StudentInfoResponse>> getStudentInfo(@AuthenticationPrincipal AccountAuthenticationContext context) {

        var response = studentService.getStudentInfo(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<StudentInfoResponse>builder()
                .statusCode(200)
                .message("Students are returned")
                .data(response)
                .build());
    }
}
