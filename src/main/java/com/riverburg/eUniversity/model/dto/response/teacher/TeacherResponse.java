package com.riverburg.eUniversity.model.dto.response.teacher;

import com.riverburg.eUniversity.model.dto.response.base.BaseUserResponse;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeacherResponse extends BaseUserResponse {

    private int id;

    private int academicDegreeId;

    private String academicDegreeName;
}
