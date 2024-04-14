package com.riverburg.eUniversity.service.journal;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.repository.JournalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JournalServiceImpl implements JournalService {

    private JournalRepository journalRepository;

    @Override
    public JournalEntity findById(int id) throws RestException {
        return journalRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.JOURNAL_NOT_FOUND);
                });
    }

    @Override
    public List<JournalEntity> findAllAttendancesByStudentAccId(UUID accountId, int scheduleId) {
        return journalRepository.getAllIsAttendedByStudentAccountIdAndScheduleId(accountId, scheduleId);
    }
}
