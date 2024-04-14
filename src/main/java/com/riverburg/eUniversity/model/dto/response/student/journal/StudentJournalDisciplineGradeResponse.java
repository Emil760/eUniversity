package com.riverburg.eUniversity.model.dto.response.student.journal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StudentJournalDisciplineGradeResponse {

    private int disciplineId;

    private String disciplineName;

    private List<StudentJournalDisciplineItemGradeResponse> grades;

}
