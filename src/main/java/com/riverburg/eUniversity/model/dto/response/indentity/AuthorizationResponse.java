package com.riverburg.eUniversity.model.dto.response.indentity;

import com.riverburg.eUniversity.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AuthorizationResponse {

    private UUID id;

    private String fullName;

    private Role role;

    private String jwtToken;

    private String refreshToken;
}
