package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.AcademicDegreeResponse;
import com.riverburg.eUniversity.service.misc.AcademicDegreeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("academic-degree")
@AllArgsConstructor
public class AcademicDegreeController {

    private final AcademicDegreeService academicDegreeService;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<List<AcademicDegreeResponse>>> getAllDegrees() {
        var result = academicDegreeService.getAllDegrees();

        return ResponseEntity.ok(BaseResponse
                .<List<AcademicDegreeResponse>>builder()
                .data(result)
                .message("All academic degrees were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDegreesDDL() {
        var result = academicDegreeService.getDegreesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All academic degrees were returned for ddl")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addDegree(@RequestBody String name) throws RestException {
        academicDegreeService.addDegree(name);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Academic degree was added")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteDegree(@RequestParam(name = "id") Integer id) throws RestException {
        academicDegreeService.deleteDegree(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Academic degree was deleted")
                .statusCode(200)
                .build());
    }
}
