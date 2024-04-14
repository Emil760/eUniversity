package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.service.bestteacher.NominationHistoryService;
import com.riverburg.eUniversity.service.bestteacher.NominationService;
import com.riverburg.eUniversity.service.bestteacher.NominationVoteService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("nominations")
@AllArgsConstructor
public class NominationController {

    private final NominationService nominationService;

    private final NominationHistoryService nominationHistoryService;

    private final NominationVoteService nominationVoteService;

    private final ValidationUtil validationUtil;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<NominationResponse>>> getNominations() {
        var result = nominationService.getNominations();

        return ResponseEntity.ok(BaseResponse
                .<List<NominationResponse>>builder()
                .data(result)
                .message("Nominations were returned")
                .statusCode(200)
                .build());
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addNomination(@NotBlank(message = "Name can`t be empty")
                                                      @RequestBody String name) throws RestException {
        validationUtil.validate(name);
        nominationService.addNomination(name);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Nomination was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("/activation/{id}")
    public ResponseEntity<BaseResponse> activateNomination(@PathVariable("id") int id,
                                                           @RequestParam("isActive") boolean isActive) {
        nominationService.activateNomination(id, isActive);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message(String.format("Nomination was %s", isActive ? "activated" : "deactivated"))
                .statusCode(200)
                .build());
    }

    @PutMapping("start")
    public ResponseEntity<BaseResponse> startNomination() {
        nominationService.startNomination();

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Nominations was started")
                .statusCode(200)
                .build());
    }

    @PutMapping("stop")
    public ResponseEntity<BaseResponse> stopNomination() {
        var votesResult = nominationVoteService.getVotesResult();
        nominationHistoryService.addNominationHistory(votesResult);
        nominationVoteService.deleteAllVotes();
        nominationService.stopNomination();

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Nominations was stopped")
                .statusCode(200)
                .build());
    }

    @GetMapping("history")
    public ResponseEntity<BaseResponse<List<NominationHistoryResponse>>> getNominationsHistory(@RequestParam("date") Date date) {
        var result = nominationHistoryService.getNominationsHistory(date);

        return ResponseEntity.ok(BaseResponse
                .<List<NominationHistoryResponse>>builder()
                .data(result)
                .message("Nominations history were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("dates-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Date>>>> getDatesDDL() {
        var result = nominationHistoryService.getDatesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Date>>>builder()
                .data(result)
                .message("Nominations dates ddl were returned")
                .statusCode(200)
                .build());
    }
}
