package com.riverburg.eUniversity.contoller.teacher;

import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalAttendance;
import com.riverburg.eUniversity.model.dto.request.post.AddStudentJournalGrade;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.JournalStudentResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherScheduleDetailResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherScheduleResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherTimeTabsResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.teacher.TeacherScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("teacher-panel/schedule")
@AllArgsConstructor
public class TeacherScheduleController {

    private final TeacherScheduleService teacherScheduleService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<TeacherScheduleResponse>>> getTeacherSchedule(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = teacherScheduleService.getTeacherSchedule(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<List<TeacherScheduleResponse>>builder()
                .statusCode(200)
                .message("Teacher`s schedule returned")
                .data(response)
                .build());
    }

    @GetMapping("{scheduleId}/detail")
    public ResponseEntity<BaseResponse<TeacherScheduleDetailResponse>> getTeacherScheduleDetail(@PathVariable int scheduleId) {
        var response = teacherScheduleService.getTeacherScheduleDetail(scheduleId);

        return ResponseEntity.ok(BaseResponse
                .<TeacherScheduleDetailResponse>builder()
                .statusCode(200)
                .message("Teacher`s detailed schedule returned")
                .data(response)
                .build());
    }

    @GetMapping("try-activate-journal")
    public ResponseEntity<BaseResponse> isLessonToday(@RequestParam("scheduleId") Integer scheduleId,
                                                      @RequestParam("date") Date date) {

        teacherScheduleService.tryActivateJournal(scheduleId, date);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Teacher`s schedule are returned")
                .build());
    }

    @GetMapping("time-tabs")
    public ResponseEntity<BaseResponse<List<TeacherTimeTabsResponse>>> getTeacherScheduleDDL(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                                             @RequestParam("groupId") Integer groupId,
                                                                             @RequestParam("disciplineId") Integer disciplineId,
                                                                             @RequestParam("date") Date date) {

        var response = teacherScheduleService.getTeacherScheduleDDL(context.getAccountId(), groupId, disciplineId, date);

        return ResponseEntity.ok(BaseResponse
                .<List<TeacherTimeTabsResponse>>builder()
                .statusCode(200)
                .message("Teacher`s schedule are returned")
                .data(response)
                .build());
    }

    @GetMapping("journal")
    public ResponseEntity<BaseResponse<List<JournalStudentResponse>>> getJournalStudents(@RequestParam("scheduleId") Integer scheduleId,
                                                                                         @RequestParam("date") Date date) {
        var response = teacherScheduleService.getJournalStudents(scheduleId, date);

        return ResponseEntity.ok(BaseResponse
                .<List<JournalStudentResponse>>builder()
                .statusCode(200)
                .message("Teacher`s journal of students are returned")
                .data(response)
                .build());
    }

    @PostMapping("grade")
    public ResponseEntity<BaseResponse> addGradeStudent(@RequestBody AddStudentJournalGrade request) {

        teacherScheduleService.addGradeStudent(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Grade added for student")
                .build());
    }

    @PostMapping("attendance")
    public ResponseEntity<BaseResponse> addAttendanceStudent(@RequestBody AddStudentJournalAttendance request) {
        teacherScheduleService.addAttendanceStudent(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("attendance added for student")
                .build());
    }
}
