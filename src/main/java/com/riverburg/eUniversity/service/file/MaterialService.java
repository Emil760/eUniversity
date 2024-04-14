package com.riverburg.eUniversity.service.file;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddMaterialRequest;
import com.riverburg.eUniversity.model.dto.response.material.MaterialResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public interface MaterialService {

    PaginatedListResponse<MaterialResponse> getPaginatedMaterialsByFacultyAndDiscipline(int sectorId, int disciplineId, PaginationRequest pagination);

    void updateMaterialDescriptionAndFileName(Integer materialId, String description, String fileName);

    void uploadMaterial(AccountAuthenticationContext accountAuthenticationContext, AddMaterialRequest request) throws IOException, RestException;

    ByteArrayResource downloadMaterial(Integer materialId) throws IOException, RestException;

    void deleteMaterial(Integer materialId) throws RestException, IOException;
}