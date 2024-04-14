package com.riverburg.eUniversity.model.dto.response.student.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class StudentProfileInfoResponse {

    private String fullName;

    private short age;

    private short score;

    private boolean isPaid;

    private String groupName;

    private String facultyName;

    private String degreeName;

    private String sectorName;

    private int semesterNumber;

    private Date startDate;
}
