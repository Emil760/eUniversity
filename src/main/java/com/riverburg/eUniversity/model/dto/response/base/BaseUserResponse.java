package com.riverburg.eUniversity.model.dto.response.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseUserResponse {

    private UUID accountId;

    private String fullName;

    private String login;

    private int age;

    private String mail;

    private boolean isActive;
}
