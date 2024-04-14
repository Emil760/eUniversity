package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddGroupMaterialRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateGroupMaterial;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.GroupMaterialResponse;
import com.riverburg.eUniversity.model.entity.GroupMaterialEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.repository.GroupMaterialRepository;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.file.FileService;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GroupMaterialServiceImpl implements GroupMaterialService {

    private final Path storagePath;

    private final GroupMaterialRepository groupMaterialRepository;

    private final EduProcessService eduProcessService;

    private final GroupService groupService;

    private final FacultyDisciplineService facultyDisciplineService;

    private final FileService fileService;

    private final DataMapper dataMapper;

    public GroupMaterialServiceImpl(@Value("${storage.directory.groups-materials}") String storageName,
                                    GroupMaterialRepository groupMaterialRepository,
                                    EduProcessService eduProcessService,
                                    GroupService groupService,
                                    FacultyDisciplineService facultyDisciplineService,
                                    FileService fileService,
                                    DataMapper dataMapper) throws IOException {
        this.groupMaterialRepository = groupMaterialRepository;
        this.eduProcessService = eduProcessService;
        this.groupService = groupService;
        this.facultyDisciplineService = facultyDisciplineService;
        this.fileService = fileService;
        this.dataMapper = dataMapper;

        this.storagePath = Paths.get(storageName).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public void uploadGroupMaterial(AccountAuthenticationContext account, AddGroupMaterialRequest request) throws IOException {
        var group = groupService.findById(request.getGroupId());
        var eduProcess = eduProcessService.findById(request.getEduProcessId());
        var facultyDiscipline = facultyDisciplineService.findById(request.getFacultyDisciplineId());

        var file = fileService
                .uploadFile(
                        Path.of(String.format("%s/%s/%s", storagePath.toString(), group.getName(), facultyDiscipline.getDisciplineEntity().getName())),
                        account.getAccountId(),
                        request.getFile()
                );

        var groupMaterial = GroupMaterialEntity
                .builder()
                .groupEntity(group)
                .eduProcessEntity(eduProcess)
                .facultyDisciplineEntity(facultyDiscipline)
                .fileEntity(file)
                .description(request.getDescription())
                .build();

        groupMaterialRepository.save(groupMaterial);
    }

    @Override
    public ByteArrayResource downloadGroupMaterial(int id) throws IOException {
        var groupMaterialEntity = findById(id);

        return fileService.downloadFile(groupMaterialEntity.getFileEntity());
    }

    @Override
    public PaginatedListResponse<GroupMaterialResponse> getGroupMaterialsOfDiscipline(int groupId, int facultyDisciplineId, int eduProcessId, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var pageMaterials = groupMaterialRepository.findMaterialsOfGroupByDisciplineAndEduProcess(
                pageable,
                groupId,
                facultyDisciplineId,
                eduProcessId,
                pagination.getSearch());

        var itemsCount = pageMaterials.getTotalElements();

        var materials = pageMaterials
                .getContent()
                .stream()
                .map(dataMapper::groupMaterialEntityToGroupMaterialResponse)
                .toList();

        return new PaginatedListResponse<GroupMaterialResponse>(materials, itemsCount);
    }

    @Override
    public void editGroupMaterial(UpdateGroupMaterial dto) throws RestException {
        var material = findById(dto.getId());

        material.setDescription(dto.getDescription());

        groupMaterialRepository.save(material);
    }

    @Override
    public void deleteGroupMaterial(int id) throws RestException {
        var material = findById(id);

        groupMaterialRepository.delete(material);
    }

    @Override
    public GroupMaterialEntity findById(int id) throws RestException {
        return groupMaterialRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.GROUP_MATERIAL_NOT_FOUND);
                });
    }
}
