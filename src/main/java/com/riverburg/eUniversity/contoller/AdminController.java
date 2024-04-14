package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateAdminRequest;
import com.riverburg.eUniversity.model.dto.response.admin.AdminResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("admins")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> registerAdmin(@RequestBody RegistrationRequest request) {
        adminService.register(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(201)
                .message("Admins's account is created")
                .build());
    }

    @GetMapping("page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<AdminResponse>>> getAdmins(PaginationRequest pagination, Integer active) {
        var response = adminService.getAdmins(pagination, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<AdminResponse>>builder()
                .statusCode(200)
                .message("Admins are returned")
                .data(response)
                .build());
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<?>> updateAdmin(@RequestBody UpdateAdminRequest request) {
        adminService.updateAdmin(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Admin was updated")
                .build());
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse<?>> activateAdmin(@PathVariable("id") UUID id,
                                                         @RequestParam("isActive") boolean isActive) {
        accountService.activateAccount(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message(String.format("Admin was %s", isActive ? "activated" : "deactivated"))
                .build());
    }

}

