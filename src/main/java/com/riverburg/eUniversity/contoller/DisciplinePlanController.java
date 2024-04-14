package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplinePlanRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.service.plan.DisciplinePlanService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("disciplines-plan")
@AllArgsConstructor
public class DisciplinePlanController {

    private DisciplinePlanService disciplinePlanService;

    private ValidationUtil validationUtil;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<List<DisciplinePlanResponse>>> getAllPlans(@RequestParam("facultyDisciplineId") Integer facultyDisciplineId) {
        var result = disciplinePlanService.getAll(facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<List<DisciplinePlanResponse>>builder()
                .data(result)
                .message("All plans were returned")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addDegree(@RequestBody AddDisciplinePlanRequest request) throws RestException {
        validationUtil.validate(request);
        disciplinePlanService.addPlan(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Plan was added")
                .statusCode(200)
                .build());
    }

}
