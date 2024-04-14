package com.riverburg.eUniversity.service.theme;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddThemeRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateThemeRequest;
import com.riverburg.eUniversity.model.dto.response.ThemeResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.entity.ThemeEntity;
import com.riverburg.eUniversity.repository.ThemeRepository;
import com.riverburg.eUniversity.service.faculty.FacultyDisciplineService;
import com.riverburg.eUniversity.service.misc.EduProcessService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {
    private final FacultyDisciplineService facultyDisciplineService;

    private final EduProcessService eduProcessService;

    private final ThemeRepository themeRepository;

    private final DataMapper dataMapper;

    @Override
    public PaginatedListResponse<ThemeResponse> getPaginatedThemesList(int facultyDisciplineId, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var themesPage = themeRepository.findByGroupAndDiscipline(facultyDisciplineId, pageable, pagination.getSearch());

        var themes = themesPage
                .getContent()
                .stream()
                .map(dataMapper::themeEntityToThemeResponse)
                .toList();

        var itemsCount = themesPage.getTotalElements();

        return new PaginatedListResponse<ThemeResponse>(themes, itemsCount);
    }

    @Override
    public void addTheme(AddThemeRequest request) throws RestException {

        validateAddTheme(request.getFacultyDisciplineId(), request.getEduProcessId(), request.getName());

        var facultyDisciplineEntity = facultyDisciplineService.findById(request.getFacultyDisciplineId());

        var eduProcessEntity = eduProcessService.findById(request.getEduProcessId());

        var order = getMaxOrder(request.getFacultyDisciplineId(), request.getEduProcessId());
        order++;

        ThemeEntity themeEntity = ThemeEntity
                .builder()
                .facultyDisciplineEntity(facultyDisciplineEntity)
                .eduProcessEntity(eduProcessEntity)
                .name(request.getName())
                .description(request.getDescription())
                .order(order)
                .from(request.getFrom())
                .to(request.getTo())
                .build();

        themeRepository.save(themeEntity);
    }

    @Override
    public void editTheme(UpdateThemeRequest request) throws RestException {

        var themeEntity = findById(request.getThemeId());

        validateUpdateTheme(themeEntity.getId(),
                themeEntity.getFacultyDisciplineEntity().getId(),
                themeEntity.getEduProcessEntity().getId(),
                request.getName(),
                request.getOrder());

        themeEntity.setName(request.getName());
        themeEntity.setDescription(request.getDescription());
        themeEntity.setOrder(request.getOrder());
        themeEntity.setFrom(request.getFrom());
        themeEntity.setTo(request.getTo());

        themeRepository.save(themeEntity);

        reorderTheme(themeEntity.getFacultyDisciplineEntity().getId(), themeEntity.getEduProcessEntity().getId());
    }

    //TODO add validation if student already posted work for this theme
    @Override
    public void deleteTheme(int id) throws RestException {
        ThemeEntity themeEntity = findById(id);

        themeRepository.delete(themeEntity);

        reorderTheme(themeEntity.getFacultyDisciplineEntity().getId(), themeEntity.getEduProcessEntity().getId());
    }

    @Override
    public ThemeEntity findById(int id) throws RestException {
        return themeRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.THEME_NOT_FOUND);
                });
    }

    @Override
    public void save(ThemeEntity themeEntity) {
        this.themeRepository.save(themeEntity);
    }

    private void validateAddTheme(int facultyDisciplineId, int eduProcessId, String name) {
        themeRepository.findByName(facultyDisciplineId, eduProcessId, name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

    private void validateUpdateTheme(int id, int facultyDisciplineId, int eduProcessId, String name, int order) {
        themeRepository.findByNameExceptCurr(id, facultyDisciplineId, eduProcessId, name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });

        var maxOrder = getMaxOrder(facultyDisciplineId, eduProcessId);

        if (order > maxOrder || order == 0)
            throw RestException.of(ErrorConstant.THEME_ORDER_IS_INCORRECT);
    }

    private short getMaxOrder(int facultyDisciplineId, int eduProcessId) {
        return (short) themeRepository.getMaxOrder(facultyDisciplineId, eduProcessId);
    }

    private void reorderTheme(int facultyDisciplineId, int eduProcessId) {
        var themes = themeRepository.findByGroupAndDisciplineAndEduProcess(facultyDisciplineId, eduProcessId);

        short order = 1;
        for (ThemeEntity theme : themes) {
            theme.setOrder(order);
            themeRepository.save(theme);
            order++;
        }
    }
}
