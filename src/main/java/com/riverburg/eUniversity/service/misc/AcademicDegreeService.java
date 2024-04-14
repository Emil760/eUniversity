package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.misc.AcademicDegreeResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.AcademicDegreeEntity;

import java.util.List;

public interface AcademicDegreeService {

    List<AcademicDegreeResponse> getAllDegrees();

    List<DDLResponse<Integer>> getDegreesDDL();

    AcademicDegreeEntity findById(Integer id) throws RestException;

    void addDegree(String name) throws RestException;

    void deleteDegree(int id) throws RestException;
}
