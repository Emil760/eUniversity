package com.riverburg.eUniversity.service.file.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.service.file.AccountFileService;
import com.riverburg.eUniversity.service.file.FileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Qualifier("accountAvatarService")
public class AccountAvatarServiceImpl implements AccountFileService {

    private final Path storagePath;

    private final FileService fileService;

    private final AccountRepository accountRepository;

    public AccountAvatarServiceImpl(@Value("${storage.directory.accounts.avatars}") String storageName,
                                    FileService fileService,
                                    AccountRepository accountRepository) throws IOException {
        this.fileService = fileService;
        this.accountRepository = accountRepository;

        this.storagePath = Paths.get(storageName).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @Override
    public ByteArrayResource download(UUID accountId) throws IOException {
        var file = accountRepository.findAvatarById(accountId);

        if (!file.isPresent())
            return null;

        return fileService.downloadFile(file.get());
    }


    @Override
    @Transactional
    public void upload(AccountAuthenticationContext accountAuthenticationContext, MultipartFile multipartFile) throws IOException {
        if (!isSupportedType(multipartFile.getContentType()))
            throw RestException.of(ErrorConstant.NOT_SUPPORTED_IMAGE);

        var currentFile = accountRepository
                .findAvatarById(accountAuthenticationContext.getAccountId());

        if (currentFile.isPresent())
            fileService.deleteFile(currentFile.get());

        var file = fileService
                .uploadFile(storagePath, accountAuthenticationContext.getAccountId(), multipartFile);

        accountRepository.updateAvatarByAccountIdAndFileId(accountAuthenticationContext.getAccountId(), file.getId());
    }

    @Override
    @Transactional
    public void uploadWithAccountId(UUID accountId, MultipartFile multipartFile) throws IOException {
        if (!isSupportedType(multipartFile.getContentType()))
            throw RestException.of(ErrorConstant.NOT_SUPPORTED_IMAGE);

        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.NOT_FOUND, "Account not found"));

        if (!Objects.isNull(account.getAvatarFileEntity()))
            fileService.deleteFile(account.getAvatarFileEntity());

        var file = fileService
                .uploadFile(storagePath, accountId, multipartFile);

        accountRepository.updateAvatarByAccountIdAndFileId(account.getId(), file.getId());
    }

    @Override
    @Transactional
    public void delete(AccountAuthenticationContext accountAuthenticationContext) throws IOException {
        var file = accountRepository.findAvatarById(accountAuthenticationContext.getAccountId())
                .orElseThrow(() -> RestException.of(ErrorConstant.NOT_FOUND, "File not found"));

        accountRepository.removeAvatarByAccountId(accountAuthenticationContext.getAccountId());

        fileService.deleteFile(file);
    }

    @Override
    @Transactional
    public void deleteWithAccountId(UUID accountId) throws IOException {
        var account = accountRepository.findByIdAndIsActive(accountId, true)
                .orElseThrow(() -> RestException.of(ErrorConstant.NOT_FOUND, "Account not found"));

        if (Objects.isNull(account.getAvatarFileEntity()))
            throw RestException.of(ErrorConstant.NOT_FOUND, "File not found");

        accountRepository.removeAvatarByAccountId(accountId);

        fileService.deleteFile(account.getAvatarFileEntity());
    }

    private boolean isSupportedType(String contentType) {
        return contentType.equals(MediaType.IMAGE_PNG_VALUE)
                || contentType.equals(MediaType.IMAGE_JPEG_VALUE);
    }
}