package com.riverburg.eUniversity.service.admin;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateAdminRequest;
import com.riverburg.eUniversity.model.dto.response.admin.AdminResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.AdminEntity;


public interface AdminService {

    void register(RegistrationRequest request);

    PaginatedListResponse<AdminResponse> getAdmins(PaginationRequest pagination, int active);

    void updateAdmin(UpdateAdminRequest request);

    AdminEntity findById(int id);
}
