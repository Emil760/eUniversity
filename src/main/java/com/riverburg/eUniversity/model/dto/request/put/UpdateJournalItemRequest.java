package com.riverburg.eUniversity.model.dto.request.put;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class UpdateJournalItemRequest {

    private Integer itemId;

    private Integer disciplineId;

    private String disciplineName;

    private Integer teacherId;

    private String teacherName;

    private Integer educationalProcessId;

    private String educationalProcessName;

    private Date date;

    private Integer dayNumber;

    private Integer assess;

    private Boolean isPresent;

    private String feedback;

}
