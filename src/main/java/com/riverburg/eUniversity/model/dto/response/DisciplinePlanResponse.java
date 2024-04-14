package com.riverburg.eUniversity.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DisciplinePlanResponse {

    private int id;

    private String eduProcessName;

    private short count;

    private short grade;
}
