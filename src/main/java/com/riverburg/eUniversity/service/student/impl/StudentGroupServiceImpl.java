package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.model.dto.response.student.group.MemberGroupResponse;
import com.riverburg.eUniversity.model.dto.response.student.group.MembersGroupResponse;
import com.riverburg.eUniversity.repository.GroupRepository;
import com.riverburg.eUniversity.service.student.StudentGroupService;
import com.riverburg.eUniversity.service.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentService studentService;

    private final GroupRepository groupRepository;

    @Override
    public MembersGroupResponse findStudentsAndTeachesOfGroup(UUID accountId) {
        var student = studentService.findByAccountId(accountId);

        var group = Objects.requireNonNull(student.getGroupEntity());

        return MembersGroupResponse
                .builder()
                .teachers(groupRepository.findTeachersByGroupId(group.getId())
                        .stream()
                        .filter(t -> Objects.nonNull(t.getAccountEntity()))
                        .map(t -> new MemberGroupResponse(t.getAccountEntity().getId(), t.getAccountEntity().getFullName()))
                        .collect(Collectors.toList()))
                .students(groupRepository.findStudentsByGroupId(group.getId())
                        .stream()
                        .filter(s -> !Objects.equals(s.getId(), student.getId()) && Objects.nonNull(s.getAccountEntity()))
                        .map(t -> new MemberGroupResponse(t.getAccountEntity().getId(), t.getAccountEntity().getFullName()))
                        .collect(Collectors.toList()))
                .build();
    }
}
