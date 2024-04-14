package com.riverburg.eUniversity.service.student;

import com.riverburg.eUniversity.model.dto.response.student.total.StudentTotalResponse;

import java.util.List;
import java.util.UUID;

public interface StudentTotalService {

    List<StudentTotalResponse> getTotals(UUID accountId);
}
