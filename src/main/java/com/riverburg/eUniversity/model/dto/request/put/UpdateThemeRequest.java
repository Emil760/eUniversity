package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@ToString
public class UpdateThemeRequest {
    private int themeId;

    @NotBlank(message = "Name can`t be empty")
    private String name;

    private String description;

    private short order;

    private Date from;

    private Date to;
}
