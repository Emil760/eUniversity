package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.group.MembersGroupResponse;

import java.util.UUID;

public interface StudentGroupService {

    MembersGroupResponse findStudentsAndTeachesOfGroup(UUID accountId);
}
