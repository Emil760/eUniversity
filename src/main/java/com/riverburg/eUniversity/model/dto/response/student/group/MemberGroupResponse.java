package com.riverburg.eUniversity.model.dto.response.student.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MemberGroupResponse {

    private UUID id;

    private String fullName;
}
