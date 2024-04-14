package com.riverburg.eUniversity.service.file.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddMaterialRequest;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.material.MaterialResponse;
import com.riverburg.eUniversity.model.entity.MaterialEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.repository.MaterialRepository;
import com.riverburg.eUniversity.service.discipline.DisciplineService;
import com.riverburg.eUniversity.service.file.FileService;
import com.riverburg.eUniversity.service.file.MaterialService;
import com.riverburg.eUniversity.service.misc.SectorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final Path storagePath;

    private final MaterialRepository materialRepository;

    private final DisciplineService disciplineService;

    private final SectorService sectorService;

    private final FileService fileService;

    private final DataMapper dataMapper;

    public MaterialServiceImpl(@Value("${storage.directory.materials}") String storageName,
                               MaterialRepository materialRepository,
                               DisciplineService disciplineService,
                               SectorService sectorService,
                               FileService fileService,
                               DataMapper dataMapper) throws IOException {

        this.materialRepository = materialRepository;
        this.disciplineService = disciplineService;
        this.sectorService = sectorService;
        this.dataMapper = dataMapper;
        this.fileService = fileService;

        this.storagePath = Paths.get(storageName).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public PaginatedListResponse<MaterialResponse> getPaginatedMaterialsByFacultyAndDiscipline(int sectorId, int disciplineId, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var pageMaterials = materialRepository.findAllByDisciplineAndSector(pageable, disciplineId, sectorId, pagination.getSearch());

        var itemsCount = pageMaterials.getTotalElements();

        var materials = pageMaterials
                .getContent()
                .stream()
                .map(dataMapper::materialEntityToMaterialResponse)
                .toList();

        return new PaginatedListResponse<>(materials, itemsCount);
    }

    @Override
    public void updateMaterialDescriptionAndFileName(Integer materialId, String description, String fileName) {
        var material = materialRepository.findById(materialId)
                .orElseThrow(() -> RestException.of(ErrorConstant.NOT_FOUND, "Material not found"));

        material.setDescription(description);
        material.getFileEntity().setOriginalFileName(fileName);

        materialRepository.save(material);
    }

    @Override
    @Transactional
    public void uploadMaterial(AccountAuthenticationContext accountAuthenticationContext, AddMaterialRequest request) throws IOException, RestException {
        var discipline = disciplineService.findActiveById(request.getDisciplineId());
        var sector = sectorService.findById(request.getSectorId());

        var path = Path.of(String.format("%s/%s/%s", storagePath.toString(), sector.getName(), discipline.getName()));

        var file = fileService
                .uploadFile(
                        path,
                        accountAuthenticationContext.getAccountId(),
                        request.getFile()
                );

        materialRepository.save(MaterialEntity
                .builder()
                .fileEntity(file)
                .disciplineEntity(discipline)
                .sectorEntity(sector)
                .description(request.getDescription())
                .build());
    }

    @Override
    public ByteArrayResource downloadMaterial(Integer materialId) throws IOException, RestException {
        var material = this.getMaterial(materialId);
        return fileService.downloadFile(material.getFileEntity());
    }

    @Override
    @Transactional
    public void deleteMaterial(Integer materialId) throws RestException, IOException {
        var material = this.getMaterial(materialId);

        materialRepository.deleteById(materialId);

        fileService.deleteFile(material.getFileEntity());
    }

    private MaterialEntity getMaterial(Integer materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> RestException.of(ErrorConstant.MATERIAL_NOT_FOUND));
    }
}