package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddStudentJournalAttendance {

    private int journalId;

    private boolean attendance;
}
