package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.DegreeResponse;
import com.riverburg.eUniversity.model.entity.DegreeEntity;

import java.util.List;

public interface DegreeService {

    List<DegreeResponse> getAllDegrees();

    List<DDLResponse<Integer>> getDegreesDDL();

    void addDegree(String name);

    void deleteDegree(int id);

    DegreeEntity findById(Integer id);
}