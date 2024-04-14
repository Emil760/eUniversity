package com.riverburg.eUniversity.service.group;


import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupRequest;
import com.riverburg.eUniversity.model.dto.response.GroupResponse;
import com.riverburg.eUniversity.model.entity.GroupEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;

import java.util.List;

public interface GroupService {

    PaginatedListResponse<GroupResponse> getPaginatedGroupList(Integer degreeId, PaginationRequest pagination);

    List<DDLResponse<Integer>> getGroupsDDL();

    List<DDLResponse<Integer>> getGroupsWithFacultiesDDL();

    List<DDLResponse<Integer>> getGroupsWithDegreesDDL();

    List<DDLResponse<Integer>> getGroupsDisciplinesDDL(int groupId);

    short getSemesterCount(int groupId);

    void addGroup(AddGroupRequest request) throws RestException;

    void deleteGroup(int id) throws RestException;

    GroupEntity findById(int id) throws RestException;

    int getStudentCountByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId);

    List<StudentEntity> getStudentsByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId);

    List<StudentEntity> getStudents(int groupId);

    List<DDLResponse<Integer>> getStudentsByGroupDDL(int groupId);

    List<DDLResponse<Integer>> getFacultyDisciplinesByGroupDDL(int groupId);
}

