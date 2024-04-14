package com.riverburg.eUniversity.model.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class JournalStudentResponse {

    int journalId;

    String studentName;

    Boolean attendance;

    Short grade;
}
