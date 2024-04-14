package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalDisciplineGradeResponse;
import com.riverburg.eUniversity.model.dto.response.student.journal.StudentJournalTotalGradeResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.student.StudentJournalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/journal")
@AllArgsConstructor
public class StudentJournalController {

    private final StudentJournalService studentJournalService;

    @GetMapping("/grades")
    public ResponseEntity<BaseResponse<StudentJournalDisciplineGradeResponse>> getGradesOfMonth(
            @AuthenticationPrincipal AccountAuthenticationContext context,
            @RequestParam("facultyDisciplineId") int facultyDisciplineId) {
        var result = studentJournalService.getStudentJournalGrades(context.getAccountId(), facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<StudentJournalDisciplineGradeResponse>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build());
    }

    @GetMapping("/grades/total-stats")
    public ResponseEntity<BaseResponse<List<Short>>> getTotalStats(
            @AuthenticationPrincipal AccountAuthenticationContext context,
            @RequestParam("facultyDisciplineId") int facultyDisciplineId
    ) {
        var result = studentJournalService.getAllGradesByDiscipline(context.getAccountId(), facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<List<Short>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build());
    }

    @GetMapping("/grades/total-grade")
    public ResponseEntity<BaseResponse<StudentJournalTotalGradeResponse>> getTotalGrade(
            @AuthenticationPrincipal AccountAuthenticationContext context,
            @RequestParam("facultyDisciplineId") int facultyDisciplineId
    ) {
        var result = studentJournalService.getTotalGradesByDiscipline(context.getAccountId(), facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<StudentJournalTotalGradeResponse>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build());
    }

}
