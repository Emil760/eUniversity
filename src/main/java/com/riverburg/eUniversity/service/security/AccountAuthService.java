package com.riverburg.eUniversity.service.security;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.security.ResetPasswordRequest;
import com.riverburg.eUniversity.model.dto.request.post.AuthorizationRequest;
import com.riverburg.eUniversity.model.dto.request.put.ChangePasswordRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.dto.response.indentity.AuthorizationResponse;
import com.riverburg.eUniversity.model.dto.response.indentity.TokensResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;

import java.util.UUID;

public interface AccountAuthService {

    AccountEntity register(RegistrationRequest request, Role role) throws RestException;

    AuthorizationResponse authorize(AuthorizationRequest request) throws RestException;

    TokensResponse refreshTokens(String refreshToken) throws RestException;

    void changeStatus(UUID accountId, boolean status) throws RestException;

    void changePassword(AccountAuthenticationContext accountAuthenticationContext, ChangePasswordRequest request) throws RestException;

    void resetPassword(ResetPasswordRequest request);

    void revokeRefreshToken(AccountAuthenticationContext accountAuthenticationContext, String refreshToken) throws RestException;


}