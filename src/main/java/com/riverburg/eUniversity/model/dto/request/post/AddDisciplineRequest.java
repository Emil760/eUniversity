package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class AddDisciplineRequest {

    @NotBlank(message = "Name can`t be empty")
    private String name;

    @NotBlank(message = "Short name can`t be empty")
    private String shortName;
}