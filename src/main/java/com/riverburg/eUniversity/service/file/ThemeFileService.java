package com.riverburg.eUniversity.service.file;


import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.post.AddThemeFileRequest;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public interface ThemeFileService {

    ByteArrayResource downloadTheme(int themeId) throws IOException, RestException;

    void uploadThemeFile(AccountAuthenticationContext accountAuthenticationContext, AddThemeFileRequest request) throws IOException, RestException;

    void deleteThemeFile(Integer themeId) throws RestException, IOException;
}