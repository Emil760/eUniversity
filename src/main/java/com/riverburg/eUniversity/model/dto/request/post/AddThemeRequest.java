package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@ToString
public class AddThemeRequest {

    private int facultyDisciplineId;

    private int eduProcessId;

    @NotBlank(message = "Name can`t be empty")
    private String name;

    private String description;

    private Date from;

    private Date to;
}
