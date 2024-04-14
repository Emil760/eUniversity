package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.EduProcessResponse;
import com.riverburg.eUniversity.model.entity.EduProcessEntity;

import java.util.List;

public interface EduProcessService {

    List<EduProcessResponse> getAllEduProcesses();

    List<DDLResponse<Integer>> getEduProcessesDDL();

    void addEduProcess(String name);

    void deleteEduProcess(int id);

    EduProcessEntity findById(Integer id);
}
