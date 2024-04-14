package com.riverburg.eUniversity.contoller.file;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupMaterialRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupMaterial;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.GroupMaterialResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.teacher.GroupMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("group-material")
@AllArgsConstructor
public class GroupMaterialController {

    private GroupMaterialService groupMaterialService;

    @PostMapping("upload")
    public ResponseEntity<BaseResponse<?>> uploadGroupMaterial(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                               @ModelAttribute AddGroupMaterialRequest request) throws IOException {

        groupMaterialService.uploadGroupMaterial(accountAuthenticationContext, request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Material for group is uploaded")
                .statusCode(200)
                .build());
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam("id") Integer id) throws IOException {
        var byteArrayResource = groupMaterialService.downloadGroupMaterial(id);

        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @GetMapping("page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<GroupMaterialResponse>>> getGroupMaterials(@RequestParam("groupId") Integer groupId,
                                                                                                        @RequestParam("facultyDisciplineId") Integer facultyDisciplineId,
                                                                                                        @RequestParam("eduProcessId") Integer eduProcessId,
                                                                                                        PaginationRequest pagination) {

        var result = groupMaterialService.getGroupMaterialsOfDiscipline(groupId, facultyDisciplineId, eduProcessId, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<GroupMaterialResponse>>builder()
                .data(result)
                .message("Group`s materials returned")
                .statusCode(200)
                .build());
    }

    @PutMapping("edit")
    public ResponseEntity<BaseResponse> editGroupMaterial(@RequestBody UpdateGroupMaterial dto) {

        groupMaterialService.editGroupMaterial(dto);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Group`s materials were changed")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteGroupMaterial(@RequestParam("id") Integer id) {

        groupMaterialService.deleteGroupMaterial(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Group`s materials were deleted")
                .statusCode(200)
                .build());
    }
}
