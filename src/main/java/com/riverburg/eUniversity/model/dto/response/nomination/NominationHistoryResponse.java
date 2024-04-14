package com.riverburg.eUniversity.model.dto.response.nomination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class NominationHistoryResponse {

    private String teacherName;

    private String nominationName;

    private Date date;

    private Integer likes;
}
