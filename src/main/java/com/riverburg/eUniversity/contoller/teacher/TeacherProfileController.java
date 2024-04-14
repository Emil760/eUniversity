package com.riverburg.eUniversity.contoller.teacher;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherNominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherProfileInfoResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.bestteacher.NominationHistoryService;
import com.riverburg.eUniversity.service.teacher.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("teacher-panel/profile")
@AllArgsConstructor
public class TeacherProfileController {

    private final TeacherService teacherService;

    private final NominationHistoryService nominationHistoryService;

    @GetMapping()
    public ResponseEntity<BaseResponse<TeacherProfileInfoResponse>> getTeacherProfileInfo(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = teacherService.getTeacherProfileInfo(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<TeacherProfileInfoResponse>builder()
                .statusCode(200)
                .message("Teacher`s profile info returned")
                .data(response)
                .build());
    }

    @GetMapping("nominations")
    public ResponseEntity<BaseResponse<List<TeacherNominationHistoryResponse>>> getTeacherNominations(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = nominationHistoryService.getTeacherNominationHistory(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<List<TeacherNominationHistoryResponse>>builder()
                .statusCode(200)
                .message("Teacher`s nominations were returned")
                .data(response)
                .build());
    }
}
