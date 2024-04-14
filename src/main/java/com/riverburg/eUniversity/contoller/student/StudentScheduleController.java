package com.riverburg.eUniversity.contoller.student;


import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleDetailResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentScheduleResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.student.StudentScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/schedule")
@AllArgsConstructor
public class StudentScheduleController {

    private final StudentScheduleService studentScheduleService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<StudentScheduleResponse>>> getStudentSchedule(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                                                          @RequestParam("semester") short semester) {
        var response = studentScheduleService.getSchedules(context.getAccountId(), semester);

        return ResponseEntity.ok(BaseResponse
                .<List<StudentScheduleResponse>>builder()
                .statusCode(200)
                .message("Students`s schedule returned")
                .data(response)
                .build());
    }

    @GetMapping("{scheduleId}/detail")
    public ResponseEntity<BaseResponse<StudentScheduleDetailResponse>> getStudentScheduleDetails(@PathVariable Integer scheduleId) {
        var response = studentScheduleService.getSchedulesDetails(scheduleId);

        return ResponseEntity.ok(BaseResponse
                .<StudentScheduleDetailResponse>builder()
                .statusCode(200)
                .message("Student`s detailed schedule returned")
                .data(response)
                .build());
    }
}
