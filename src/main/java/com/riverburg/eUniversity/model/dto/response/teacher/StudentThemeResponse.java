package com.riverburg.eUniversity.model.dto.response.teacher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class StudentThemeResponse {

    private Integer themeId;

    private  Integer studentWorkId;

    private String themeName;

    private String themeFileName;

    private Long studentFileId;

    private String studentFileName;

    private String themeDescription;

    private String feedback;

    private Short grade;

    private Date from;

    private Date to;

    private Short order;
}
