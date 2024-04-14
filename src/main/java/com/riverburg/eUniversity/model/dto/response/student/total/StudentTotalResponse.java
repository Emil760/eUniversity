package com.riverburg.eUniversity.model.dto.response.student.total;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StudentTotalResponse {

    private int disciplineId;

    private String disciplineName;

    private List<StudentPlanTotalResponse> totals;
}
