package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;

import java.util.List;
import java.util.UUID;

public interface TeacherGroupService {

    List<DDLResponse<Integer>> getTeacherGroupsDDL(UUID accountId);

    List<DDLResponse<Integer>> getTeacherGroupsDisciplinesDDL(UUID accountId, int groupId);
}
