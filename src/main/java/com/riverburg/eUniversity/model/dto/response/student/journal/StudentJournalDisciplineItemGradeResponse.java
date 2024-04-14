package com.riverburg.eUniversity.model.dto.response.student.journal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class StudentJournalDisciplineItemGradeResponse {

    private Date date;

    private Short grade;

    private String eduProcessName;
}
