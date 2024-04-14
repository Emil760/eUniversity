package com.riverburg.eUniversity.service.file.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.post.AddThemeFileRequest;
import com.riverburg.eUniversity.model.entity.ThemeEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.service.file.FileService;
import com.riverburg.eUniversity.service.file.ThemeFileService;
import com.riverburg.eUniversity.service.theme.ThemeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ThemeFileServiceImpl implements ThemeFileService {

    private final Path storagePath;

    private final ThemeService themeService;

    private final FileService fileService;


    public ThemeFileServiceImpl(@Value("${storage.directory.themes}") String storageName,
                                ThemeService themeService,
                                FileService fileService) throws IOException {

        this.themeService = themeService;
        this.fileService = fileService;

        this.storagePath = Paths.get(storageName).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public ByteArrayResource downloadTheme(int themeId) throws IOException, RestException {
        var theme = themeService.findById(themeId);

        return fileService.downloadFile(theme.getFileEntity());
    }

    @Override
    @Transactional
    public void uploadThemeFile(AccountAuthenticationContext accountAuthenticationContext, AddThemeFileRequest request) throws IOException, RestException {
        var theme = themeService.findById(request.getThemeId());

        fileService.deleteFile(theme.getFileEntity());

        var path = Path.of(String.format("%s/%s/%s",
                storagePath.toString(),
                theme.getFacultyDisciplineEntity().getFacultyEntity().getName(),
                theme.getFacultyDisciplineEntity().getDisciplineEntity().getName()));

        var file = fileService
                .uploadFile(
                        path,
                        accountAuthenticationContext.getAccountId(),
                        request.getFile()
                );

        theme.setFileEntity(file);

        themeService.save(theme);
    }

    @Override
    @Transactional
    public void deleteThemeFile(Integer themeId) throws RestException, IOException {
        ThemeEntity themeEntity = themeService.findById(themeId);

        fileService.deleteFile(themeEntity.getFileEntity());

        themeEntity.setFileEntity(null);

        themeService.save(themeEntity);
    }
}