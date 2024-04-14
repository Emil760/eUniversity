package com.riverburg.eUniversity.service.plan;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplinePlanRequest;
import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;

import java.util.List;

public interface DisciplinePlanService {

    List<DisciplinePlanResponse> getAll(int facultyDisciplineId);

    void addPlan(AddDisciplinePlanRequest request) throws RestException;
}
