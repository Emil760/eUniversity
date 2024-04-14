package com.riverburg.eUniversity.service.theme;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddThemeRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateThemeRequest;
import com.riverburg.eUniversity.model.dto.response.ThemeResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.ThemeEntity;

public interface ThemeService {

    PaginatedListResponse<ThemeResponse> getPaginatedThemesList(int facultyDisciplineId, PaginationRequest pagination);

    void addTheme(AddThemeRequest request) throws RestException;

    void editTheme(UpdateThemeRequest request) throws RestException;

    void deleteTheme(int id) throws RestException;

    ThemeEntity findById(int id) throws RestException;

    void save(ThemeEntity themeEntity);
}
