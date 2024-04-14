package com.riverburg.eUniversity.service.file;

import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface AccountFileService {

    ByteArrayResource download(UUID accountId) throws IOException;

    void upload(AccountAuthenticationContext accountAuthenticationContext, MultipartFile multipartFile) throws IOException;

    void uploadWithAccountId(UUID accountId, MultipartFile multipartFile) throws IOException;

    void delete(AccountAuthenticationContext accountAuthenticationContext) throws IOException;

    void deleteWithAccountId(UUID accountId) throws IOException;
}