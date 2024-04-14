package com.riverburg.eUniversity.contoller;

import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.service.configuration.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("configuration")
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<String>> getConfiguration(@RequestParam("config") String config) {
        var result = configurationService.getStringConfig(config);

        return ResponseEntity.ok(BaseResponse
                .<String>builder()
                .data(result)
                .message("Configuration was returned")
                .statusCode(200)
                .build());
    }
}
