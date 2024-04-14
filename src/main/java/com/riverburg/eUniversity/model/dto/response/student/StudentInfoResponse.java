package com.riverburg.eUniversity.model.dto.response.student;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentInfoResponse {

    int studentId;

    int groupId;

    int facultyId;

    int degreeId;

    int sectorId;
}
