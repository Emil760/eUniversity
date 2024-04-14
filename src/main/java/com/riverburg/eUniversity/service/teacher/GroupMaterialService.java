package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupMaterialRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupMaterial;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.GroupMaterialResponse;
import com.riverburg.eUniversity.model.entity.GroupMaterialEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;


public interface GroupMaterialService {

    void uploadGroupMaterial(AccountAuthenticationContext account, AddGroupMaterialRequest request) throws IOException;

    ByteArrayResource downloadGroupMaterial(int id) throws IOException;

    PaginatedListResponse<GroupMaterialResponse> getGroupMaterialsOfDiscipline(int groupId, int facultyDisciplineId, int eduProcessId, PaginationRequest pagination);

    void editGroupMaterial(UpdateGroupMaterial dto) throws RestException;

    GroupMaterialEntity findById(int id) throws RestException;

    void deleteGroupMaterial(int id) throws RestException;
}
