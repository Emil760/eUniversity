package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.repository.user.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherGroupServiceImpl implements TeacherGroupService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<DDLResponse<Integer>> getTeacherGroupsDDL(UUID accountId) {
        return teacherRepository.getTeacherGroupsDDL(accountId);
    }

    @Override
    public List<DDLResponse<Integer>> getTeacherGroupsDisciplinesDDL(UUID accountId, int groupId) {
        return teacherRepository.getTeacherGroupsDisciplinesDDL(accountId, groupId);
    }

}
