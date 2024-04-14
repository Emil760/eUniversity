package com.riverburg.eUniversity.contoller.student;

import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.request.post.NominateRequest;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.bestteacher.NominationHistoryService;
import com.riverburg.eUniversity.service.bestteacher.NominationService;
import com.riverburg.eUniversity.service.bestteacher.NominationVoteService;
import com.riverburg.eUniversity.service.configuration.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("student-panel/nomination")
@AllArgsConstructor
public class StudentNominationController {

    private final AccountService accountService;

    private final NominationService nominationService;

    private final NominationHistoryService nominationHistoryService;

    private final NominationVoteService nominationVoteService;

    private final ConfigurationService configurationService;

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

    @GetMapping("date-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Date>>>> getDatesDDL() {
        var result = nominationHistoryService.getDatesDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Date>>>builder()
                .data(result)
                .message("Nominations dates ddl were returned")
                .statusCode(200)
                .build());
    }

    @GetMapping("is-active")
    public ResponseEntity<BaseResponse<Boolean>> getNominationsHistory() {
        var result = configurationService.isActive("NOMINATION_START");

        return ResponseEntity.ok(BaseResponse
                .<Boolean>builder()
                .data(result)
                .message(String.format("Nominations is %s", result ? "active" : "inactive"))
                .statusCode(200)
                .build());
    }

    @GetMapping("teachers-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<UUID>>>> getAccountsDDL() {
        var response = accountService.getAccountsDDL(Role.TEACHER);

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<UUID>>>builder()
                .statusCode(200)
                .message("Teachers ddl where returned")
                .data(response)
                .build());
    }

    @GetMapping("nominations-ddl")
    public ResponseEntity<BaseResponse<List<DDLResponse<Integer>>>> getNominationsDDL() {
        var response = nominationService.getNominationsDDL();

        return ResponseEntity.ok(BaseResponse
                .<List<DDLResponse<Integer>>>builder()
                .statusCode(200)
                .message("Teachers ddl where returned")
                .data(response)
                .build());
    }

    @PostMapping("/nominate")
    public ResponseEntity<BaseResponse<?>> nominate(@AuthenticationPrincipal AccountAuthenticationContext context,
                                                    @RequestBody NominateRequest request) {
        nominationVoteService.nominate(context.getAccountId(), request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .statusCode(201)
                .message("Nomination was made successfully")
                .build());
    }
}