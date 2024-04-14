package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.SectorResponse;
import com.riverburg.eUniversity.model.entity.SectorEntity;
import com.riverburg.eUniversity.repository.GroupRepository;
import com.riverburg.eUniversity.repository.SectorRepository;
import com.riverburg.eUniversity.repository.TeacherSectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    private final GroupRepository groupRepository;

    private final TeacherSectorRepository teacherSectorRepository;

    private final DataMapper dataMapper;

    @Override
    public List<SectorResponse> getAllSectors() {
        var sectors = sectorRepository.findAll();

        return sectors
                .stream()
                .map(dataMapper::sectorEntityToSectorResponse)
                .toList();
    }

    @Override
    public List<DDLResponse<Integer>> getSectorsDDL() {
        return sectorRepository.findAllDDL();
    }

    @Override
    public void addSector(String name) throws RestException {
        validateSector(name);

        SectorEntity sectorEntity = new SectorEntity();

        sectorEntity.setName(name);

        sectorRepository.save(sectorEntity);
    }

    @Override
    public void deleteSector(int id) throws RestException {
        findById(id);

        if (!groupRepository.findBySector(id).isEmpty())
            throw RestException.of(ErrorConstant.SECTOR_USED_BY_GROUP);

        if (!teacherSectorRepository.findBySector(id).isEmpty())
            throw RestException.of(ErrorConstant.SECTOR_USED_BY_TEACHER);

        sectorRepository.deleteById(id);
    }

    public SectorEntity findById(Integer id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.SECTOR_NOT_FOUND);
                });
    }

    private void validateSector(String name) {
 //       if (name == null || name.isBlank())
 //           throw RestException.of(ErrorConstant.EMPTY_PARAMETER, "Name is empty");

        sectorRepository.findByName(name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

}
