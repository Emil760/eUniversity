package com.riverburg.eUniversity.model.dto.response.student.discipline;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentDisciplineHWStatsResponse {

    private Integer disciplineId;

    private Short completedHWs;

    private Short totalHWs;
}
