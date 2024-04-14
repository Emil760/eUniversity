package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ToString
public class AddGroupRequest {

    private LocalDate startDate;

    private Integer degreeId;

    private Integer facultyId;

    private Integer sectorId;
}

