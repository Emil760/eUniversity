package com.riverburg.eUniversity.model.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupMaterialResponse {

    Integer id;

    String fileName;

    String description;

    String eduProcessName;
}
