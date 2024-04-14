package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.EduProcessResponse;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("edu-processes")
@AllArgsConstructor
public class EduProcessesController {

    private final EduProcessService eduProcessService;

    private final ValidationUtil validationUtil;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<List<EduProcessResponse>>> getAllEduProcesses() {
        var result = eduProcessService.getAllEduProcesses();

        return ResponseEntity.ok(BaseResponse
                .<List<EduProcessResponse>>builder()
                .data(result)
                .message("All educational processes were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getEduProcessesDDL() {
        var result = eduProcessService.getEduProcessesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All educational processes were returned for ddl")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addEduProcess(@NotBlank(message = "Name can`t be empty")
                                                      @RequestBody
                                                      String name) throws RestException {
        validationUtil.validate(name);
        eduProcessService.addEduProcess(name);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Educational processes was added")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteEduProcess(@RequestParam(name = "id") Integer id) throws RestException {
        eduProcessService.deleteEduProcess(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Educational processes was deleted")
                .statusCode(200)
                .build());
    }
}
