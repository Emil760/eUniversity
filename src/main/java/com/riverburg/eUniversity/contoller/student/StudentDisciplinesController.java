package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.material.MaterialResponse;
import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineNextLessonResponse;
import com.riverburg.eUniversity.model.dto.response.student.discipline.StudentDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.GroupMaterialResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.file.MaterialService;
import com.riverburg.eUniversity.service.plan.DisciplinePlanService;
import com.riverburg.eUniversity.service.student.StudentDisciplineService;
import com.riverburg.eUniversity.service.teacher.GroupMaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/disciplines")
@AllArgsConstructor
public class StudentDisciplinesController {

    private final StudentDisciplineService studentDisciplineService;

    private final DisciplinePlanService disciplinePlanService;

    private final GroupMaterialService groupMaterialService;

    private final MaterialService materialService;

    private final FacultyDisciplineService facultyDisciplineService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<StudentDisciplineResponse>>> findAllStudentDisciplines(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) {
        var result = studentDisciplineService.findAllStudentDisciplines(accountAuthenticationContext.getAccountId());
        return ResponseEntity.ok(
                BaseResponse
                        .<List<StudentDisciplineResponse>>builder()
                        .message("Success")
                        .statusCode(200)
                        .data(result)
                        .build()
        );
    }

    @GetMapping("/next-lessons-dates")
    public ResponseEntity<BaseResponse<List<StudentDisciplineNextLessonResponse>>> findAllStudentNextLessons(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) {
        var result = studentDisciplineService.getCalculatedNextDisciplinesLessons(accountAuthenticationContext.getAccountId());
        return ResponseEntity.ok(
                BaseResponse
                        .<List<StudentDisciplineNextLessonResponse>>builder()
                        .message("Success")
                        .statusCode(200)
                        .data(result)
                        .build()
        );
    }

    @GetMapping(value = "/disciplines-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getDisciplinesDDL(@RequestParam("facultyId") int facultyId,
                                                                                      @RequestParam("degreeId") int degreeId) {

        var result = facultyDisciplineService.getActiveDisciplinesListDDL(facultyId, degreeId);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }

    @GetMapping(value = "/discipline-plan")
    public ResponseEntity<BaseResponse<List<DisciplinePlanResponse>>> getDisciplinePlan(@RequestParam("facultyDisciplineId") Integer facultyDisciplineId) {

        var result = disciplinePlanService.getAll(facultyDisciplineId);

        return ResponseEntity.ok(BaseResponse
                .<List<DisciplinePlanResponse>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }

    @GetMapping(value = "edu-materials")
    public ResponseEntity<BaseResponse<PaginatedListResponse<MaterialResponse>>> getEduMaterials(@RequestParam("sectorId") Integer sectorId,
                                                                                                 @RequestParam("disciplineId") Integer disciplineId,
                                                                                                 PaginationRequest pagination) {

        var result = materialService.getPaginatedMaterialsByFacultyAndDiscipline(sectorId, disciplineId, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<MaterialResponse>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }

    @GetMapping(value = "discipline-materials")
    public ResponseEntity<BaseResponse<PaginatedListResponse<GroupMaterialResponse>>> getDisciplineMaterials(@RequestParam("groupId") Integer groupId,
                                                                                                             @RequestParam("facultyDisciplineId") Integer facultyDisciplineId,
                                                                                                             @RequestParam("eduProcessId") Integer eduProcessId,
                                                                                                             PaginationRequest pagination) {

        var result = groupMaterialService.getGroupMaterialsOfDiscipline(groupId, facultyDisciplineId, eduProcessId, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<GroupMaterialResponse>>builder()
                .message("Success")
                .statusCode(200)
                .data(result)
                .build()
        );
    }


    //TODO refactor
//    @GetMapping("/short-homeworks-stats")
//    public ResponseEntity<BaseResponse<List<StudentDisciplineHWStatsResponse>>> findAllStudentShortHWStats(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) {
//        var result = studentHomeworkService.findAllStudentHWShortStats(accountAuthenticationContext.getAccountId());
//        return ResponseEntity.ok(
//                BaseResponse
//                        .<List<StudentDisciplineHWStatsResponse>>builder()
//                        .message("Success")
//                        .statusCode(200)
//                        .data(result)
//                        .build()
//        );
//    }


}
