package com.riverburg.eUniversity.service.discipline;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateDisciplineRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplineResponse;
import com.riverburg.eUniversity.model.entity.DisciplineEntity;

import java.util.List;

public interface DisciplineService {
    PaginatedListResponse<DisciplineResponse> getPaginatedDisciplineList(PaginationRequest pagination, int active);

    List<DDLResponse<Integer>> getDisciplineListDDL();

    List<DDLResponse<Integer>> getDisciplineListShortDDL();

    void addDiscipline(AddDisciplineRequest request) throws RestException;

    void updateDiscipline(UpdateDisciplineRequest request) throws RestException;

    void deleteDiscipline(int id) throws RestException;

    void activateDiscipline(int id, boolean isActive);

    DisciplineEntity findActiveById(int id);

}