package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.dto.request.post.NominateRequest;
import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;
import com.riverburg.eUniversity.model.entity.NominationVoteEntity;
import com.riverburg.eUniversity.repository.NominationVoteRepository;
import com.riverburg.eUniversity.service.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NominationVoteServiceImpl implements NominationVoteService {

    private final NominationVoteRepository nominationVoteRepository;

    private final AccountService accountService;

    private final NominationService nominationService;

    @Override
    public void nominate(UUID accountId, NominateRequest request) {
        alreadyNominated(accountId, request.getNominationId());

        var account = accountService.findById(accountId);

        var teacher = accountService.findById(request.getTeacherId());

        var nomination = nominationService.findById(request.getNominationId());

        NominationVoteEntity entity = NominationVoteEntity
                .builder()
                .accountEntity(account)
                .teacherEntity(teacher)
                .nominationEntity(nomination)
                .build();

        nominationVoteRepository.save(entity);
    }

    @Override
    public void deleteAllVotes() {
        nominationVoteRepository.deleteAll();
    }

    @Override
    public List<INominationVotesResponse> getVotesResult() {

        ArrayList<INominationVotesResponse> result = new ArrayList<>();

        INominationVotesResponse currVote = null;

        var nominationIds = nominationService.getActiveNominationIds();

        var votes = nominationVoteRepository.getAllVotes();

        if (votes == null || votes.size() == 0)
            return result;

        for (Integer nominationId : nominationIds) {
            for (INominationVotesResponse vote : votes) {
                if (vote.getNominationId() == nominationId && (currVote == null || currVote.getVotes() <= vote.getVotes())) {
                    currVote = vote;
                }
            }

            if (currVote != null) {
                result.add(currVote);
                currVote = null;
            }
        }

        return result;
    }

    private void alreadyNominated(UUID accountId, int nominationId) {
        if (nominationVoteRepository.isAlreadyNominated(accountId.toString(), nominationId)) {
            throw RestException.of(ErrorConstant.NOMINATION_ALREADY_MADE);
        }
    }

}