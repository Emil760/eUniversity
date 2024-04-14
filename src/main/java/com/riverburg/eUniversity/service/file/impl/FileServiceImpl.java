package com.riverburg.eUniversity.service.file.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.entity.FileEntity;
import com.riverburg.eUniversity.repository.FileRepository;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final AccountService accountService;

    @Override
    public FileEntity uploadFile(Path rootPath, UUID accountId, MultipartFile multipartFile) throws IOException {
        var account = accountService.findById(accountId);

        String fileName = UUID.randomUUID().toString();
        String extension = "." + StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        Path fullPath = rootPath.resolve(fileName + extension);
        Files.createDirectories(rootPath);

        Files.copy(multipartFile.getInputStream(), fullPath);

        return fileRepository.save(
                new FileEntity(
                        fullPath.toString(),
                        fileName,
                        Objects.requireNonNull(multipartFile.getOriginalFilename()),
                        extension,
                        account));
    }

    @Override
    public ByteArrayResource downloadFile(FileEntity file) throws IOException, RestException {

        if (file == null)
            throw RestException.of(ErrorConstant.NOT_FOUND);

        return new ByteArrayResource(
                Files.readAllBytes(Paths.get(file.getFilePath()))
        );
    }

    @Override
    public ByteArrayResource downloadFile(String fullPath) throws IOException {

        if (fullPath.isBlank())
            throw RestException.of(ErrorConstant.SERVER_ERROR);

        return new ByteArrayResource(
                Files.readAllBytes(Paths.get(fullPath))
        );
    }

    @Override
    public void deleteFile(FileEntity file) throws IOException {
        if (file == null)
            return;

        fileRepository.delete(file);
        Files.delete(Paths.get(file.getFilePath()));
    }

}
