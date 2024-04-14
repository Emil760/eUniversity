package com.riverburg.eUniversity.model.dto.response.student.profile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentProfileStatsResponse {

    private Integer homeworksCount;

    private Integer homeworksDoneCount;

    private Double averageHWsMark;

    private Double averageJournalMark;

    private Integer examCount;

    private Integer passedExamCount;
}
