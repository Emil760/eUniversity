package com.riverburg.eUniversity.model.dto.response.student.journal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class StudentJournalTotalGradeResponse {

    private List<Short> personalWorks;

    private List<Short> seminars;

    @ApiModelProperty(example = "{yes: 0, no: 0}")
    private Map<String, Integer> attendance;

    private List<Short> labs;
}
