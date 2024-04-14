package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    private String oldPassword;

    private String newPassword;
}
