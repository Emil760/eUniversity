package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.*;
import com.riverburg.eUniversity.model.dto.request.put.UpdateTeacherRequest;
import com.riverburg.eUniversity.model.dto.response.ScheduleResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.*;
import com.riverburg.eUniversity.service.teacher.TeacherDisciplineService;
import com.riverburg.eUniversity.service.teacher.TeacherGroupService;
import com.riverburg.eUniversity.service.teacher.TeacherSectorService;
import com.riverburg.eUniversity.service.teacher.TeacherService;
import com.riverburg.eUniversity.service.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("teachers")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final AccountService accountService;

    private final TeacherDisciplineService teacherDisciplineService;

    private final TeacherSectorService teacherSectorService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> register(@RequestBody RegisterTeacherRequest request) {
        teacherService.register(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(201)
                .message("Teacher's account is created")
                .build());
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<PaginatedListResponse<TeacherResponse>>> getTeachers(PaginationRequest pagination,
                                                                                            @RequestParam("active") Integer active) {
        var response = teacherService.getTeachers(pagination, active);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<TeacherResponse>>builder()
                .statusCode(200)
                .message("Teachers are returned")
                .data(response)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getTeachersDDL() {
        var response = teacherService.getActiveTeachersDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .message("Teachers are returned for ddl")
                .data(response)
                .build());
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<?>> updateTeacher(@RequestBody UpdateTeacherRequest request) {
        teacherService.update(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Teacher updated")
                .build());
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse<?>> updateTeacher(@PathVariable("id") UUID id,
                                                         @RequestParam("isActive") boolean isActive) {
        accountService.activateAccount(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message(String.format("Teacher was %s", isActive ? "activated" : "deactivated"))
                .build());
    }

    @PostMapping("/add-discipline")
    public ResponseEntity<BaseResponse<?>> addDiscipline(@RequestBody AddDisciplineToTeacher request) {
        teacherDisciplineService.addDiscipline(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Discipline is added to teacher")
                .build());
    }

    @GetMapping("/disciplines")
    public ResponseEntity<BaseResponse<List<TeacherDisciplineResponse>>> getDisciplines(@RequestParam("teacherId") int teacherId) {
        var response = teacherDisciplineService.getDisciplines(teacherId);

        return ResponseEntity.ok(BaseResponse
                .<List<TeacherDisciplineResponse>>builder()
                .statusCode(200)
                .message("Disciplines of teacher are returned")
                .data(response)
                .build());
    }

    @GetMapping("/teachers-with-disciplines")
    public ResponseEntity<BaseResponse<PaginatedListResponse<TeacherDisciplinesResponse>>> getDisciplinesTeachers(PaginationRequest pagination) {
        var response = teacherDisciplineService.getTeachersDisciplines(pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<TeacherDisciplinesResponse>>builder()
                .statusCode(200)
                .message("Teachers with disciplines are returned")
                .data(response)
                .build());
    }

    @DeleteMapping("/delete-discipline")
    public ResponseEntity<BaseResponse<?>> deleteDiscipline(@RequestParam("teacherId") Integer teacherId,
                                                            @RequestParam(name = "disciplineId") Integer disciplineId) {
        teacherDisciplineService.deleteDiscipline(teacherId, disciplineId);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Discipline was deleted from teacher")
                .build());
    }

    @PostMapping("/add-sector")
    public ResponseEntity<BaseResponse<?>> addSector(@RequestBody AddSectorToTeacher request) {
        teacherSectorService.addSector(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Sector was added to teacher")
                .build());
    }

    @GetMapping("/sectors")
    public ResponseEntity<BaseResponse<List<TeacherSectorResponse>>> getSectors(@RequestParam("teacherId") Integer teacherId) {
        var response = teacherSectorService.getSectors(teacherId);

        return ResponseEntity.ok(BaseResponse
                .<List<TeacherSectorResponse>>builder()
                .statusCode(200)
                .message("Sectors of teacher are returned")
                .data(response)
                .build());
    }

    @GetMapping("/teachers-with-sectors")
    public ResponseEntity<BaseResponse<PaginatedListResponse<TeacherSectorsResponse>>> getTeachersSectors(PaginationRequest pagination) {
        var response = teacherSectorService.getTeachersSectors(pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<TeacherSectorsResponse>>builder()
                .statusCode(200)
                .message("Teachers with sectors are returned")
                .data(response)
                .build());
    }

    @GetMapping("disciplined-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinedTeachersDDL(@RequestParam("disciplineId") Integer disciplineId) {

        var result = teacherDisciplineService.getDisciplinedTeachersDDL(disciplineId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("disciplined teachers were returned")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/delete-sector")
    public ResponseEntity<BaseResponse<?>> deleteSector(@RequestParam("teacherId") Integer teacherId,
                                                        @RequestParam(name = "sectorId") Integer sectorId) {
        teacherSectorService.deleteSector(teacherId, sectorId);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(200)
                .message("Sector was deleted from teacher")
                .build());
    }

    @GetMapping("/teachers-discipline-sector-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getTeacherDisciplineSector(@RequestParam("disciplineId") Integer disciplineId,
                                                                                               @RequestParam("sectorId") Integer sectorId) {
        var response = teacherService.getTeacherDisciplineSector(disciplineId, sectorId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .message("Teachers with sectors are returned")
                .data(response)
                .build());
    }
}
