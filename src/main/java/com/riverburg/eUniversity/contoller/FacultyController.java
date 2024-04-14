package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineToFacultyRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddFacultyRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.FacultyDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.FacultyResponse;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.faculty.FacultyService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("faculties")
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    private final FacultyDisciplineService facultyDisciplineService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<FacultyResponse>>> getPaginatedFaculties(PaginationRequest pagination,
                                                                                                      @RequestParam("active") Integer active) {
        var result = facultyService.getPaginatedFacultyList(pagination, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<FacultyResponse>>builder()
                .data(result)
                .message("Faculties were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("/ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getFacultiesDDL() {
        var result = facultyService.getFacultyListDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Faculties were returned for ddl")
                .statusCode(200)
                .build()
        );
    }

    @GetMapping("/short-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getFacultiesShortDDL() {
        var result = facultyService.getFacultyListShortDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Faculties were returned for ddl")
                .statusCode(200)
                .build()
        );
    }

    @GetMapping("/semester-count")
    public ResponseEntity<BaseResponse<Short>> getSemesterCount(@RequestParam("facultyId") Integer facultyId) {
        var result = facultyService.getSemesterCount(facultyId);

        return ResponseEntity.ok(BaseResponse
                .<Short>builder()
                .data(result)
                .message("SemesterDates count returned")
                .statusCode(200)
                .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addFaculty(@RequestBody AddFacultyRequest request) throws RestException {
        validationUtil.validate(request);
        facultyService.addFaculty(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Faculty was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("/edit")
    public ResponseEntity<BaseResponse> editFaculty(@RequestBody UpdateFacultyRequest request) throws RestException {
        validationUtil.validate(request);
        facultyService.updateFaculty(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Faculty was updated")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteFaculty(@RequestParam("id") Integer id) throws RestException {
        facultyService.deleteFaculty(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Faculty was deleted")
                .statusCode(200)
                .build());
    }


    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse> activateFaculty(@PathVariable("id") int id,
                                                        @RequestParam("isActive") boolean isActive) {
        facultyService.activateFaculty(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message(String.format("Faculty was %s", isActive ? "activated" : "deactivated"))
                .statusCode(200)
                .build());
    }

    @GetMapping("discipline-page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<FacultyDisciplineResponse>>> getDisciplines(PaginationRequest pagination,
                                                                                                         @RequestParam("degreeId") Integer degreeId,
                                                                                                         @RequestParam("facultyId") Integer facultyId,
                                                                                                         @RequestParam("active")  Integer active) {
        var result = facultyDisciplineService.getDisciplines(pagination, degreeId, facultyId, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<FacultyDisciplineResponse>>builder()
                .data(result)
                .message("Disciplines was returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("disciplines-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinesDDL(@RequestParam("facultyId") Integer facultyId,
                                                                                      @RequestParam("degreeId") Integer degreeId) {

        var result = facultyDisciplineService.getActiveDisciplinesListDDL(facultyId, degreeId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Active disciplines were returned for ddl")
                .statusCode(200)
                .build()
        );
    }

    @PostMapping("/assign-discipline")
    public ResponseEntity<BaseResponse> addDiscipline(@RequestBody AddDisciplineToFacultyRequest request) {
        validationUtil.validate(request);
        facultyDisciplineService.addDiscipline(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Discipline was assigned")
                .statusCode(200)
                .build());
    }

    @PutMapping("/activate-discipline/{id}")
    public ResponseEntity<BaseResponse> activateDiscipline(@PathVariable("id") Integer id,
                                                           @RequestParam("isActive") Boolean isActive) {
        facultyDisciplineService.activateDiscipline(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message(String.format("Discipline was %s", "activated"))
                .statusCode(200)
                .build());
    }

    //TODO add validation
    @DeleteMapping("/delete-discipline")
    public ResponseEntity<BaseResponse> removeDiscipline(@RequestParam("id") Integer id) {
        facultyDisciplineService.removeDiscipline(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Discipline was unassigned")
                .statusCode(200)
                .build());
    }
}
