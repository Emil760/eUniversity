package com.riverburg.eUniversity.model.dto.response.nomination;

import java.util.UUID;

public interface INominationVotesResponse {

    int nominationId = 0;

    UUID accountId = null;

    int votes = 0;

    int getNominationId();

    UUID getAccountId();

    int getVotes();
}
