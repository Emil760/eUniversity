package com.riverburg.eUniversity.service.group;


import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupRequest;
import com.riverburg.eUniversity.model.dto.response.GroupResponse;
import com.riverburg.eUniversity.model.entity.*;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import com.riverburg.eUniversity.repository.GroupRepository;
import com.riverburg.eUniversity.repository.user.StudentRepository;
import com.riverburg.eUniversity.service.misc.DegreeService;
import com.riverburg.eUniversity.service.misc.SectorService;
import com.riverburg.eUniversity.service.faculty.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final StudentRepository studentRepository;

    private final DegreeService degreeService;

    private final FacultyService facultyService;

    private final SectorService sectorService;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final DataMapper dataMapper;

    @Override
    public PaginatedListResponse<GroupResponse> getPaginatedGroupList(Integer degreeId, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var pageGroups = groupRepository.findByDegreeGroups(pageable, degreeId, pagination.getSearch());

        var itemsCount = pageGroups.getTotalElements();

        var groups = pageGroups.toList();

        return new PaginatedListResponse<GroupResponse>(groups, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getGroupsDDL() {
        return groupRepository.getGroupsDDL();
    }

    @Override
    public List<DDLResponse<Integer>> getGroupsWithFacultiesDDL() {
        return groupRepository.getGroupsWithFacultiesDDL();
    }

    @Override
    public List<DDLResponse<Integer>> getGroupsWithDegreesDDL() {
        return groupRepository.getGroupsWithDegreesDDL();
    }

    @Override
    public List<DDLResponse<Integer>> getGroupsDisciplinesDDL(int groupId) {
        return groupRepository.getGroupsDisciplinesDDL(groupId);
    }

    @Override
    public short getSemesterCount(int groupId) {
        return groupRepository.getSemesterCount(groupId);
    }

    @Override
    public void addGroup(AddGroupRequest request) throws RestException {
        var count = (long) groupRepository.findByFaculty(request.getFacultyId()).size();
        count++;

        DegreeEntity degreeEntity = degreeService.findById(request.getDegreeId());

        FacultyEntity facultyEntity = facultyService.findActiveById(request.getFacultyId());

        SectorEntity sectorEntity = sectorService.findById(request.getSectorId());

        GroupEntity groupEntity = GroupEntity
                .builder()
                .name(facultyEntity.getShortName() + "-" + count)
                .startDate(request.getStartDate())
                .degreeEntity(degreeEntity)
                .facultyEntity(facultyEntity)
                .sectorEntity(sectorEntity)
                .build();

        groupRepository.save(groupEntity);
    }

    @Override
    public void deleteGroup(int id) throws RestException {
        findById(id);

        var students = studentRepository.findByGroup(id);

        for (StudentEntity student : students) {
            student.setGroupEntity(null);
        }

        studentRepository.saveAll(students);
        groupRepository.deleteById(id);
    }

    @Override
    public GroupEntity findById(int id) throws RestException {
        return groupRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.GROUP_NOT_FOUND);
                });
    }

    @Override
    public int getStudentCountByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId) {
        return groupRepository.getStudentCountByFacultyAndDegree(year, facultyId, degreeId);
    }

    @Override
    public List<StudentEntity> getStudentsByFacultyAndDegreeAndYear(short year, int facultyId, int degreeId) {
        return groupRepository.findStudentsByFacultyAndDegree(year, facultyId, degreeId);
    }

    @Override
    public List<StudentEntity> getStudents(int groupId) {
        return groupRepository.findStudentsByGroupId(groupId);
    }

    @Override
    public List<DDLResponse<Integer>> getStudentsByGroupDDL(int groupId) {
        return studentRepository
                .findByGroup(groupId)
                .stream()
                .map(e -> new DDLResponse<>(e.getId(), e.getAccountEntity().getFullName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DDLResponse<Integer>> getFacultyDisciplinesByGroupDDL(int groupId) {
        return facultyDisciplineRepository
                .findAllByGroupId(groupId)
                .stream()
                .map(e -> new DDLResponse<>(e.getId(), e.getDisciplineEntity().getShortName()))
                .collect(Collectors.toList());
    }

}

