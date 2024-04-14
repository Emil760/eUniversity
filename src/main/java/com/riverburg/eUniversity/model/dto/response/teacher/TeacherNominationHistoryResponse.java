package com.riverburg.eUniversity.model.dto.response.teacher;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TeacherNominationHistoryResponse {

    private String nominationName;

    private Date date;
}
