package com.riverburg.eUniversity.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PaidFacultyResponse {

    private int id;

    private  int facultyId;

    private String facultyName;

    private int degreeId;

    private String degreeName;

    private short year;

    private short freeCount;
}
