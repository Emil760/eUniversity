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
@RequestMapping("file/avatar")
public class AccountAvatarController {

    private final AccountFileService accountAvatarService;

    public AccountAvatarController(@Qualifier("accountAvatarService") AccountFileService accountAvatarService) {
        this.accountAvatarService = accountAvatarService;
    }

    @GetMapping
    @ApiOperation("Find account's avatar by account id")
    public ResponseEntity<?> getAvatar(@RequestParam("accountId") UUID accountId) throws IOException {
        var byteArrayResource = accountAvatarService.download(accountId);
        return ResponseEntity
                .ok()
                .contentLength(Objects.isNull(byteArrayResource) ? 0 : byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @PostMapping("/add")
    @ApiOperation("Set avatar to authorized user (changes your avatar)")
    public ResponseEntity<BaseResponse<?>> setAvatar(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                     @RequestParam("file") MultipartFile multipartFile) throws IOException {
        accountAvatarService.upload(accountAuthenticationContext, multipartFile);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is uploaded")
                .statusCode(200)
                .build());
    }

    @PostMapping("/add-by-account-id")
    @ApiOperation("Set avatar to specified user by account id (this endpoint can be used only by ADMIN)")
    public ResponseEntity<BaseResponse<?>> setAvatarByAccount(@RequestParam("account_id") UUID accountId,
                                                              @RequestParam("file") MultipartFile multipartFile) throws IOException {
        accountAvatarService.uploadWithAccountId(accountId, multipartFile);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is uploaded")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove")
    @ApiOperation("Delete avatar of authorized user (your avatar)")
    public ResponseEntity<BaseResponse<?>> deleteAvatar(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext) throws IOException {
        accountAvatarService.delete(accountAuthenticationContext);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is deleted")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove-by-account-id")
    @ApiOperation("Remove avatar from specified user by account id (this endpoint can be used only by ADMIN)")
    public ResponseEntity<BaseResponse<?>> deleteAvatarByAccount(@RequestParam("account_id") UUID accountId) throws IOException {
        accountAvatarService.deleteWithAccountId(accountId);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Avatar is deleted")
                .statusCode(200)
                .build());
    }
}
