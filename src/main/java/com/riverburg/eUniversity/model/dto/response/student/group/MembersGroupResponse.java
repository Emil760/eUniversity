package com.riverburg.eUniversity.model.dto.response.student.group;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class MembersGroupResponse {

    private Integer groupId;

    private List<MemberGroupResponse> students;

    private List<MemberGroupResponse> teachers;
}
