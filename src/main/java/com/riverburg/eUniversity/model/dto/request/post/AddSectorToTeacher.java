package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddSectorToTeacher {

    private int teacherId;

    private int sectorId;
}
