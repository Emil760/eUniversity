package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.total.StudentTotalResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.student.StudentTotalService;
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
@RequestMapping("student-panel/total")
@AllArgsConstructor
public class StudentTotalController {

    private final StudentTotalService studentTotalService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<StudentTotalResponse>>> getTotals(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var result = studentTotalService.getTotals(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<List<StudentTotalResponse>>builder()
                .data(result)
                .message("Totals were returned")
                .statusCode(200)
                .build());
    }
}
