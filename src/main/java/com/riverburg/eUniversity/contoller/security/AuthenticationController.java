package com.riverburg.eUniversity.contoller.security;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.security.ResetPasswordRequest;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import com.riverburg.eUniversity.model.dto.request.post.AuthorizationRequest;
import com.riverburg.eUniversity.model.dto.request.put.ChangePasswordRequest;
import com.riverburg.eUniversity.model.dto.response.indentity.AuthorizationResponse;
import com.riverburg.eUniversity.model.dto.response.indentity.TokensResponse;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.configuration.ConfigurationService;
import com.riverburg.eUniversity.service.mail.MailSenderService;
import com.riverburg.eUniversity.service.security.AccountAuthService;
import com.riverburg.eUniversity.util.validation.ValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/authentication")
@Api(value = "Authentication controller", description = "Designed for authorization and token generation")
@AllArgsConstructor
public class AuthenticationController {

    private final AccountAuthService accountAuthService;

    private final ValidationUtil validationUtil;

    private final MailSenderService mailSenderService;

    private final ConfigurationService configurationService;

    @PostMapping
    @ApiOperation("Accepts login and password, if the entered data is correct, method returns *Access* and *Refresh* tokens")
    public ResponseEntity<BaseResponse<AuthorizationResponse>> authorize(@RequestBody AuthorizationRequest request) throws RestException {
        validationUtil.validate(request);
        var result = accountAuthService.authorize(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse
                        .<AuthorizationResponse>builder()
                        .statusCode(201)
                        .message("Tokens are created successfully")
                        .data(result)
                        .build());
    }

    @PostMapping("/refresh-tokens/{rt}")
    @ApiOperation("Accepts refresh token as Header \"Rt\", if refresh token is correct, method returns new *Access* and *Refresh* tokens")
    public ResponseEntity<BaseResponse<TokensResponse>> refreshTokens(@PathVariable(name = "rt") String rt) throws RestException {
        var result = accountAuthService.refreshTokens(rt);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse
                        .<TokensResponse>builder()
                        .statusCode(201)
                        .message("Tokens are created successfully")
                        .data(result)
                        .build());
    }

    @PutMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                                       @RequestBody ChangePasswordRequest request) {
        accountAuthService.changePassword(accountAuthenticationContext, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(BaseResponse
                        .builder()
                        .statusCode(202)
                        .message("Password is changed, tokens are revorked")
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse> logout(@AuthenticationPrincipal AccountAuthenticationContext accountAuthenticationContext,
                                               @RequestHeader(name = "Rt") String rt) {
        accountAuthService.revokeRefreshToken(accountAuthenticationContext, rt);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(BaseResponse
                        .builder()
                        .message("Token revoked")
                        .statusCode(202)
                        .build());
    }

    @PostMapping("/reset-password/offer")
    public ResponseEntity<BaseResponse<?>> offerResetPassword(@RequestBody String mail) {
        mailSenderService.sendPasswordResetMail(mail);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(BaseResponse
                        .builder()
                        .message("Mail is sent")
                        .statusCode(202)
                        .build());
    }

    @PostMapping("/reset-password/accept")
    public ResponseEntity<BaseResponse<?>> acceptResetPassword(@RequestBody ResetPasswordRequest request) {
        accountAuthService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(BaseResponse
                        .builder()
                        .message("Password is changed")
                        .statusCode(202)
                        .build());
    }

}
