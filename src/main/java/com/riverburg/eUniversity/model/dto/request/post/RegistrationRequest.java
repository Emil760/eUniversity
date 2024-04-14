package com.riverburg.eUniversity.model.dto.request.post;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String login;

    private String password;

    private String fullName;

    private String mail;

    private int age;
}
