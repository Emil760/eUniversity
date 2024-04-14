package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherNominationHistoryResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface NominationHistoryService {

    void addNominationHistory(List<INominationVotesResponse> votesResult);

    List<NominationHistoryResponse> getNominationsHistory(Date date);

    List<TeacherNominationHistoryResponse> getTeacherNominationHistory(UUID accountId);

    List<DDLResponse<Date>> getDatesDDL();

}
