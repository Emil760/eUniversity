package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateDisciplineRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplineResponse;
import com.riverburg.eUniversity.service.discipline.DisciplineService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("disciplines")
@AllArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<DisciplineResponse>>> getPaginatedDisciplines(PaginationRequest pagination, Integer active) {
        var result = disciplineService.getPaginatedDisciplineList(pagination, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<DisciplineResponse>>builder()
                .data(result)
                .message("Disciplines were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("/ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinesDDL() {
        var result = disciplineService.getDisciplineListDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Disciplines were returned for ddl")
                .statusCode(200)
                .build()
        );
    }

    @GetMapping("/short-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinesShortDDL() {
        var result = disciplineService.getDisciplineListShortDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Disciplines were returned for ddl")
                .statusCode(200)
                .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addDisciplines(@RequestBody AddDisciplineRequest request) throws RestException {
        validationUtil.validate(request);
        disciplineService.addDiscipline(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Discipline was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("/edit")
    public ResponseEntity<BaseResponse> editDisciplines(@RequestBody UpdateDisciplineRequest request) throws RestException {
        validationUtil.validate(request);
        disciplineService.updateDiscipline(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Discipline was updated")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteDiscipline(@RequestParam("id") Integer id) throws RestException {
        disciplineService.deleteDiscipline(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Discipline was deleted")
                .statusCode(200)
                .build());
    }


    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse> activateDiscipline(@PathVariable("id") int id,
                                                           @RequestParam("isActive") boolean isActive) {
        disciplineService.activateDiscipline(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message(String.format("Discipline was %s", isActive ? "activated" : "deactivated"))
                .statusCode(200)
                .build());
    }
}
