package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddSectorToTeacher;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorsResponse;
import com.riverburg.eUniversity.model.entity.TeachersSectorEntity;
import com.riverburg.eUniversity.repository.TeacherSectorRepository;
import com.riverburg.eUniversity.service.misc.SectorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherSectorServiceImpl implements TeacherSectorService {

    private final TeacherSectorRepository teacherSectorRepository;

    private final TeacherService teacherService;

    private final SectorService sectorService;

    @Override
    public void addSector(AddSectorToTeacher request) {
        validate(request.getTeacherId(), request.getSectorId());

        var teacher = teacherService.findById(request.getTeacherId());

        var sector = sectorService.findById(request.getSectorId());

        TeachersSectorEntity entity = TeachersSectorEntity
                .builder()
                .teacherEntity(teacher)
                .sectorEntity(sector)
                .build();

        teacherSectorRepository.save(entity);
    }

    @Override
    public List<TeacherSectorResponse> getSectors(Integer teacherId) {
        return teacherSectorRepository.findSectors(teacherId);
    }

    @Override
    public PaginatedListResponse<TeacherSectorsResponse> getTeachersSectors(PaginationRequest pagination) {
        var teachers = teacherSectorRepository.findTeachersSectors(
                pagination.getPageIndex(),
                pagination.getPageSize(),
                pagination.getSearch());

        var itemsCount = teacherSectorRepository.getTeachersSectorsCount(pagination.getSearch());

        return new PaginatedListResponse<TeacherSectorsResponse>(teachers, itemsCount);
    }

    //TODO maybe add some validation
    @Override
    public void deleteSector(int teacherId, int sectorId) {
        var sector = findByTeacherAndSector(teacherId, sectorId);

        teacherSectorRepository.delete(sector);
    }

    private TeachersSectorEntity findByTeacherAndSector(int teacherId, int sectorId) {
        return teacherSectorRepository.findByTeacherAndSector(teacherId, sectorId)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.SECTOR_NOT_FOUND);
                });
    }


    private void validate(int teacherId, int sectorId) {
        teacherSectorRepository.findByTeacherAndSector(teacherId, sectorId)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
