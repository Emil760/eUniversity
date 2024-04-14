package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.model.dto.request.post.NominateRequest;
import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;

import java.util.List;
import java.util.UUID;

public interface NominationVoteService {

    void nominate(UUID accountId, NominateRequest request);

    void deleteAllVotes();

    List<INominationVotesResponse> getVotesResult();
}