package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationResponse;
import com.riverburg.eUniversity.model.entity.NominationEntity;

import java.util.List;

public interface NominationService {

    List<NominationResponse> getNominations();

    List<Integer> getActiveNominationIds();

    void addNomination(String name);

    void activateNomination(int id, boolean isActive);

    NominationEntity findById(int id) throws RestException;

    List<DDLResponse<Integer>> getNominationsDDL();

    void startNomination();

    void stopNomination();
}
