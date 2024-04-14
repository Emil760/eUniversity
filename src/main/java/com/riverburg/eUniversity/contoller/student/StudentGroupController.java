package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.student.group.MembersGroupResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.student.StudentGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/groups")
@AllArgsConstructor
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    @GetMapping(value = "/all-members")
    public ResponseEntity<BaseResponse<MembersGroupResponse>> findAllMembersOfGroup(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) {
        var result = studentGroupService.findStudentsAndTeachesOfGroup(accountAuthenticationContext.getAccountId());
        return ResponseEntity.ok(BaseResponse
                .<MembersGroupResponse>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }
}
