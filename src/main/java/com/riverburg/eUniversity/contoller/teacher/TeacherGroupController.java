package com.riverburg.eUniversity.contoller.teacher;

import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.plan.DisciplinePlanService;
import com.riverburg.eUniversity.service.teacher.TeacherGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("teacher-panel/groups")
@AllArgsConstructor
public class TeacherGroupController {

    private final TeacherGroupService teacherGroupService;

    private final DisciplinePlanService disciplinePlanService;

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getGroupsDLL(@AuthenticationPrincipal AccountAuthenticationContext context) {
        var response = teacherGroupService.getTeacherGroupsDDL(context.getAccountId());

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .message("Teacher`s groups ddl are returned")
                .data(response)
                .build());
    }

    @GetMapping("{groupId}/disciplines-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinesDLL(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                                                      @PathVariable("groupId") Integer groupId) {

        var response = teacherGroupService.getTeacherGroupsDisciplinesDDL(context.getAccountId(), groupId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .message("Teacher`s disciplines ddl are returned")
                .data(response)
                .build());
    }

    @GetMapping(value = "/discipline-plan")
    public ResponseEntity<BaseResponse<List<DisciplinePlanResponse>>> getDisciplinePlan(@RequestParam("facultyDisciplineId") Integer facultyDisciplineId) {

        var result = disciplinePlanService.getAll(facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<List<DisciplinePlanResponse>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }
}
