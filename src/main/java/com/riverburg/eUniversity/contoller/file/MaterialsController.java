package com.riverburg.eUniversity.contoller.file;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddMaterialRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateMaterialRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.material.MaterialResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.file.MaterialService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("material")
@AllArgsConstructor
public class MaterialsController {

    private final MaterialService materialService;

    @GetMapping("/all")
    @ApiOperation("Get all materials by sector and discipline")
    public ResponseEntity<BaseResponse<PaginatedListResponse<MaterialResponse>>> findAllMaterials(@RequestParam("disciplineId") Integer disciplineId,
                                                                                                  @RequestParam("sectorId") Integer sectorId,
                                                                                                  PaginationRequest pagination) {
        var result = materialService.getPaginatedMaterialsByFacultyAndDiscipline(sectorId, disciplineId, pagination);
        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<MaterialResponse>>builder()
                .statusCode(200)
                .message("Materials are returned")
                .data(result)
                .build());
    }

    @GetMapping("/download")
    @ApiOperation("Download material by id")
    public ResponseEntity<?> download(@RequestParam("materialId") Integer materialId) throws IOException {
        var byteArrayResource = materialService.downloadMaterial(materialId);
        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @PutMapping("/update")
    @ApiOperation("Update original file name and description of material")
    public ResponseEntity<BaseResponse<?>> update(@RequestBody UpdateMaterialRequest materialRequest) {
        materialService.updateMaterialDescriptionAndFileName(materialRequest.getId(), materialRequest.getDescription(), materialRequest.getOriginalFileName());
        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Material is changed")
                .build());
    }

    @PostMapping("/upload")
    @ApiOperation("Add new material")
    public ResponseEntity<BaseResponse<?>> uploadMaterial(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                          @ModelAttribute AddMaterialRequest materialRequest) throws IOException {
        materialService.uploadMaterial(accountAuthenticationContext, materialRequest);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Material is uploaded")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove")
    @ApiOperation("Delete material by id")
    public ResponseEntity<BaseResponse<?>> deleteMaterial(@RequestParam("materialId") Integer materialId) throws IOException {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Material is deleted")
                .statusCode(200)
                .build());
    }
}
