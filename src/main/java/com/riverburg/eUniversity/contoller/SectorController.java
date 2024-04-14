package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.SectorResponse;
import com.riverburg.eUniversity.service.misc.SectorService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("sectors")
@AllArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    private final ValidationUtil validationUtil;

    @GetMapping("all")
    public ResponseEntity<BaseResponse<List<SectorResponse>>> getAllSectors() {
        var result = sectorService.getAllSectors();

        return ResponseEntity.ok(BaseResponse
                .<List<SectorResponse>>builder()
                .data(result)
                .message("All sectors were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getSectorsDDL() {
        var result = sectorService.getSectorsDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .data(result)
                .message("All sectors were returned for ddl")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addSector(@NotBlank(message = "Name can`t be empty")
                                                  @RequestBody
                                                  String name) throws RestException {
        validationUtil.validate(name);
        sectorService.addSector(name);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Sector was added")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("delete")
    public ResponseEntity<BaseResponse> deleteSector(@RequestParam(name = "id") Integer id) throws RestException {
        sectorService.deleteSector(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Sector was deleted")
                .statusCode(200)
                .build());
    }
}
