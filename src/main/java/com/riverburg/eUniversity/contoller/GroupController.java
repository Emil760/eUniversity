package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupRequest;
import com.riverburg.eUniversity.model.dto.response.GroupResponse;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<GroupResponse>>> getAllSectors(Integer degreeId, PaginationRequest pagination) {
        var result = groupService.getPaginatedGroupList(degreeId, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<GroupResponse>>builder()
                .data(result)
                .message("groups were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getGroupsDDL() {
        var result = groupService.getGroupsDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All groups were returned for ddl")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl-with-faculties")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getGroupsWithFacultiesDDL() {
        var result = groupService.getGroupsWithFacultiesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All groups with faculties were returned for ddl")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl-with-degrees")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getGroupsWithDegreesDDL() {
        var result = groupService.getGroupsWithDegreesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All groups with degrees were returned for ddl")
                .statusCode(200)
                .build());
    }

    @GetMapping("disciplines")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getGroupsDisciplinesDDL(@RequestParam("groupId") Integer groupId) {
        var result = groupService.getGroupsDisciplinesDDL(groupId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("Activated disciplines of group were returned for ddl")
                .statusCode(200)
                .build());
    }

    @GetMapping("/semester-count")
    public ResponseEntity<BaseResponse<Short>> getSemesterCount(@RequestParam("groupId") Integer groupId) {
        var result = groupService.getSemesterCount(groupId);

        return ResponseEntity.ok(BaseResponse
                .<Short>builder()
                .data(result)
                .message("Semester count returned")
                .statusCode(200)
                .build()
        );
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addGroup(@RequestBody AddGroupRequest request) throws RestException {
        validationUtil.validate(request);
        groupService.addGroup(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Group was added")
                .statusCode(200)
                .build());
    }

    //TODO add validation
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteGroup(@RequestParam("id") Integer id) throws RestException {
        groupService.deleteGroup(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Group was deleted")
                .statusCode(200)
                .build());
    }

}
