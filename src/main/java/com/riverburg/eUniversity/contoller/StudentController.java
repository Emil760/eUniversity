package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegisterStudentRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateStudentRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentResponse;
import com.riverburg.eUniversity.service.student.StudentService;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final AccountService accountService;

    private final ValidationUtil validationUtil;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> registerStudent(@RequestBody RegisterStudentRequest request) {
        validationUtil.validate(request);
        studentService.register(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(201)
                .message("Student's account is created")
                .build());
    }

    @GetMapping("page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<StudentResponse>>> getStudents(PaginationRequest pagination,
                                                                                            @RequestParam("groupId") Integer groupId,
                                                                                            @RequestParam("active") Integer active) {

        var response = studentService.getStudent(pagination, groupId, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<StudentResponse>>builder()
                .statusCode(200)
                .message("Students are returned")
                .data(response)
                .build());
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<?>> updateStudent(@RequestBody UpdateStudentRequest request) {
        validationUtil.validate(request);
        studentService.updateStudent(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Student was updated")
                .build());
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse<?>> activateStudent(@PathVariable("id") UUID id,
                                                           @RequestParam("isActive") boolean isActive) {
        accountService.activateAccount(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message(String.format("Student was %s", isActive ? "activated" : "deactivated"))
                .build());
    }

}
