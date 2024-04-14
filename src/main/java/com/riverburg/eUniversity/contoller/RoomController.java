package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddRoomRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateRoomRequest;
import com.riverburg.eUniversity.model.dto.response.RoomResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.service.room.RoomService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<RoomResponse>>> getAllRooms(PaginationRequest request,
                                                                                         @RequestParam("active") Integer active) {
        var result = roomService.getPaginatedRooms(request, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<RoomResponse>>builder()
                .data(result)
                .message("All rooms were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getRoomsDDL() {
        var result = roomService.getRoomsDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All rooms were returned for ddl")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addRoom(@RequestBody AddRoomRequest request) throws RestException {
        validationUtil.validate(request);
        roomService.addRoom(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Room was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("edit")
    public ResponseEntity<BaseResponse> editRoom(@RequestBody UpdateRoomRequest request) throws RestException {
        validationUtil.validate(request);
        roomService.editRoom(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Room was updated")
                .statusCode(200)
                .build());
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse> activateRoom(@PathVariable("id") int id,
                                                     @RequestParam("isActive") boolean isActive) {
        roomService.activateRoom(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message(String.format("Room was %s", isActive ? "activated" : "deactivated"))
                .statusCode(200)
                .build());
    }

    //TODO add validation
    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteRoom(@RequestParam(name = "id") Integer id) throws RestException {
        roomService.deleteRoom(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Room was deleted")
                .statusCode(200)
                .build());
    }
}
