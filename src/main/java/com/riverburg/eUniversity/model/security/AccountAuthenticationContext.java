package com.riverburg.eUniversity.model.security;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AccountAuthenticationContext {

    private UUID accountId;

    private String username;
}
