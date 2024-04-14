package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.SectorResponse;
import com.riverburg.eUniversity.model.entity.SectorEntity;

import java.util.List;

public interface SectorService {

    List<SectorResponse> getAllSectors();

    List<DDLResponse<Integer>> getSectorsDDL();

    SectorEntity findById(Integer id);

    void addSector(String name);

    void deleteSector(int id);
}
