package com.riverburg.eUniversity.service.journal;

import com.riverburg.eUniversity.model.entity.JournalEntity;

import java.util.List;
import java.util.UUID;

public interface JournalService {

    JournalEntity findById(int id);

    List<JournalEntity> findAllAttendancesByStudentAccId(UUID accountId, int scheduleId);

}
