package com.riverburg.eUniversity.model.dto.response.indentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokensResponse {

    private String accessToken;

    private String refreshToken;
}
