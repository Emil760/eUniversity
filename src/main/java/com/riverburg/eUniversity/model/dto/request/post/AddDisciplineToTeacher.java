package com.riverburg.eUniversity.model.dto.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddDisciplineToTeacher {

    private int teacherId;

    private int disciplineId;
}
