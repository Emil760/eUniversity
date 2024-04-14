package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddPaidFaculty;
import com.riverburg.eUniversity.model.dto.request.put.UpdatePaidFaculty;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyResponse;
import com.riverburg.eUniversity.model.dto.response.PaidFacultyStudentResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.service.faculty.PaidFacultyService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("paid-faculty")
@AllArgsConstructor
public class PaidFacultyController {

    private final PaidFacultyService paidFacultyService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<PaidFacultyResponse>>> getAllPaidFaculties(Integer year, PaginationRequest pagination) {
        var result = paidFacultyService.getPaginatedPaidFaculties(year, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<PaidFacultyResponse>>builder()
                .data(result)
                .message("paid faculties were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("students")
    public ResponseEntity<BaseResponse<List<PaidFacultyStudentResponse>>> getAllPaidStudents(@RequestParam("year") Short year,
                                                                                             @RequestParam("facultyId") Integer facultyId,
                                                                                             @RequestParam("degreeId") Integer degreeId) {

        var result = paidFacultyService.getAllPaidStudents(year, facultyId, degreeId);

        return ResponseEntity.ok(BaseResponse
                .<List<PaidFacultyStudentResponse>>builder()
                .data(result)
                .message("paid students were returned")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addPaidFaculty(@RequestBody AddPaidFaculty request) throws RestException {
        validationUtil.validate(request);
        paidFacultyService.addPaidFaculty(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("paid faculty was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("edit")
    public ResponseEntity<BaseResponse> editPaidFaculty(@RequestBody UpdatePaidFaculty request) throws RestException {
        validationUtil.validate(request);
        paidFacultyService.editPaidFaculty(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("paid faculty was updated")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponse> deletePaidFaculty(@RequestParam("id") Integer id) throws RestException {
        paidFacultyService.deletePaidFaculty(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("paid faculty was deleted")
                .statusCode(200)
                .build());
    }
}
