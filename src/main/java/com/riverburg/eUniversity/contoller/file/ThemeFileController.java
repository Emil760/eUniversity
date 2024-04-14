package com.riverburg.eUniversity.contoller.file;


import com.riverburg.eUniversity.model.dto.request.post.AddThemeFileRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.file.ThemeFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("theme-file")
@AllArgsConstructor
public class ThemeFileController {

    private final ThemeFileService themeFileService;

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam("themeId") Integer themeId) throws IOException {
        var byteArrayResource = themeFileService.downloadTheme(themeId);
        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<?>> upload(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                  @ModelAttribute AddThemeFileRequest request) throws IOException {
        themeFileService.uploadThemeFile(accountAuthenticationContext, request);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("File is uploaded")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<BaseResponse<?>> deleteThemeFile(@RequestParam("themeId") Integer themeId) throws IOException {
        themeFileService.deleteThemeFile(themeId);
        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("file is deleted")
                .statusCode(200)
                .build());
    }
}
