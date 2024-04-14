package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.DegreeResponse;
import com.riverburg.eUniversity.service.misc.DegreeService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("degrees")
@AllArgsConstructor
public class DegreeController {

    private final DegreeService degreeService;

    private final ValidationUtil validationUtil;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<List<DegreeResponse>>> getAllDegrees() {
        var result = degreeService.getAllDegrees();

        return ResponseEntity.ok(BaseResponse
                .<List<DegreeResponse>>builder()
                .data(result)
                .message("All degrees were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDegreesDDL() {
        var result = degreeService.getDegreesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All degrees were returned for ddl")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addDegree(@NotBlank(message = "Name can`t be empty")
                                                  @RequestBody
                                                  String name) throws RestException {
        validationUtil.validate(name);
        degreeService.addDegree(name);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Degree was added")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteDegree(@RequestParam(name = "id") Integer id) throws RestException {
        degreeService.deleteDegree(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Degree was deleted")
                .statusCode(200)
                .build());
    }
}
