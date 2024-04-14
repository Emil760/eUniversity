package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class UpdateGroupRequest {

    private Integer id;

    @NotBlank(message = "Name can`t be empty")
    private String name;
}

