package com.riverburg.eUniversity.model.dto.response.student.homework;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class StudentHomeworkResponse {

    private Long fileId;

    private String fileName;

    private int themeId;

    private int studentWorkId;

    private Long studentFileId;

    private String studentFileName;

    private String themeName;

    private String description;

    private Date from;

    private Date to;

    private Short grade;

    private String feedback;

    private Boolean isUploaded;

    private Date uploadedDate;
}
