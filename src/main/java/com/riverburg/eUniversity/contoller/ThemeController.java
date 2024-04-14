package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddThemeRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateThemeRequest;
import com.riverburg.eUniversity.model.dto.response.ThemeResponse;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.service.theme.ThemeService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("theme")
@AllArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    private final ValidationUtil validationUtil;

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<PaginatedListResponse<ThemeResponse>>> getAllThemes(Integer facultyDisciplineId, PaginationRequest pagination) {
        var result = themeService.getPaginatedThemesList(facultyDisciplineId, pagination);

        return ResponseEntity.ok(BaseResponse
                .<PaginatedListResponse<ThemeResponse>>builder()
                .data(result)
                .message("Themes were returned")
                .statusCode(200)
                .build());
    }

    @PostMapping("add")
    public ResponseEntity<BaseResponse> addTheme(@RequestBody AddThemeRequest request) throws RestException {
        validationUtil.validate(request);
        themeService.addTheme(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Theme was added")
                .statusCode(200)
                .build());
    }

    @PutMapping("edit")
    public ResponseEntity<BaseResponse> editTheme(@RequestBody UpdateThemeRequest request) throws RestException {
        validationUtil.validate(request);
        themeService.editTheme(request);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Theme was updated")
                .statusCode(200)
                .build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> deleteTheme(@RequestParam("id") Integer id) throws RestException {
        themeService.deleteTheme(id);

        return ResponseEntity.ok(BaseResponse
                .builder()
                .message("Theme was deleted")
                .statusCode(200)
                .build());
    }
}
