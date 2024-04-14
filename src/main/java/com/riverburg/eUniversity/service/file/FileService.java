package com.riverburg.eUniversity.service.file;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.entity.FileEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface FileService {

    FileEntity uploadFile(Path rootPath, UUID accountId, MultipartFile multipartFile) throws IOException;

    ByteArrayResource downloadFile(FileEntity fileEntity) throws IOException, RestException;

    ByteArrayResource downloadFile(String fullPath) throws IOException;

    void deleteFile(FileEntity fileEntity) throws IOException;
}