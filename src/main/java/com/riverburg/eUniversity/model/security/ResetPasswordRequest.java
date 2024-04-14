package com.riverburg.eUniversity.model.security;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    private String mailToken;

    private String newPassword;
}

