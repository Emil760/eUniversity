package com.riverburg.eUniversity.contoller.file;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.file.AccountFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("file/description")
public class AccountDescriptionController {

    private final AccountFileService accountDescriptionService;

    public AccountDescriptionController(@Qualifier("accountDescriptionService") AccountFileService accountDescriptionService) {
        this.accountDescriptionService = accountDescriptionService;
    }

    @GetMapping
    @ApiOperation("Find account's description by account id")
    public ResponseEntity<?> getDescription(@RequestParam("accountId") UUID accountId) throws IOException {
        var byteArrayResource = accountDescriptionService.download(accountId);
        return ResponseEntity
                .ok()
                .contentLength(Objects.isNull(byteArrayResource) ? 0 : byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @PostMapping("/add")
    @ApiOperation("Set description to authorized user (changes your avatar)")
    public ResponseEntity<BaseResponse<?>> setDescription(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                     @RequestParam("file") MultipartFile multipartFile) throws IOException {
        accountDescriptionService.upload(accountAuthenticationContext, multipartFile);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is uploaded")
                .statusCode(200)
                .build());
    }

    @PostMapping("/add-by-account-id")
    @ApiOperation("Set description to specified user by account id (this endpoint can be used only by ADMIN)")
    public ResponseEntity<BaseResponse<?>> setDescriptionByAccount(@RequestParam("account_id") UUID accountId,
                                                              @RequestParam("file") MultipartFile multipartFile) throws IOException {
        accountDescriptionService.uploadWithAccountId(accountId, multipartFile);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is uploaded")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove")
    @ApiOperation("Delete description of authorized user (your description)")
    public ResponseEntity<BaseResponse<?>> deleteDescription(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) throws IOException {
        accountDescriptionService.delete(accountAuthenticationContext);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is deleted")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove-by-account-id")
    @ApiOperation("Remove description from specified user by account id (this endpoint can be used only by ADMIN)")
    public ResponseEntity<BaseResponse<?>> deleteDescriptionByAccount(@RequestParam("account_id") UUID accountId) throws IOException {
        accountDescriptionService.deleteWithAccountId(accountId);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is deleted")
                .statusCode(200)
                .build());
    }
}
