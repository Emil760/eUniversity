package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.model.dto.request.post.AddScheduleRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateScheduleRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.ScheduleResponse;
import com.riverburg.eUniversity.service.schedule.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("schedule")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/type-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getTypeDDL() {
        var result = scheduleService.getTypeDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("schedule type were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<ScheduleResponse>>> getSchedules(@RequestParam("groupId") Integer groupId,
                                                                             @RequestParam("semesterNumber") Short semesterNumber) {

        var result = scheduleService.getScheduleList(groupId, semesterNumber);

        return ResponseEntity.ok(BaseResponse
                .<List<ScheduleResponse>>builder()
                .data(result)
                .message("schedule were returned")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addSchedule(@RequestBody AddScheduleRequest request) {

        scheduleService.addSchedule(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Schedule was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("edit")
    public ResponseEntity<BaseResponse> editSchedule(@RequestBody UpdateScheduleRequest request) {

        scheduleService.editSchedule(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Schedule was added")
                .statusCode(200)
                .build());
    }
}
