package com.riverburg.eUniversity.model.dto.request.put;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBaseUser {

    protected UUID accountId;

    protected String fullName;

    protected String mail;

    protected Integer age;
}
