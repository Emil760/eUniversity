package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherNominationHistoryResponse;
import com.riverburg.eUniversity.model.entity.NominationHistoryEntity;
import com.riverburg.eUniversity.repository.NominationHistoryRepository;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.configuration.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NominationHistoryServiceImpl implements NominationHistoryService {

    private final NominationHistoryRepository nominationHistoryRepository;

    private final ConfigurationService configurationService;

    private final AccountService accountService;

    private final NominationService nominationService;

    private final DataMapper dataMapper;

    @Override
    public void addNominationHistory(List<INominationVotesResponse> votesResult) {

        if (votesResult == null || votesResult.size() == 0)
            return;

        ArrayList<NominationHistoryEntity> nominationHistoryEntities = new ArrayList<>();

        var date = configurationService.getDateConfig("NOMINATION_START");

        for (INominationVotesResponse vote : votesResult) {

            var account = accountService.findById(vote.getAccountId());

            var nomination = nominationService.findById(vote.getNominationId());

            NominationHistoryEntity entity = NominationHistoryEntity.
                    builder()
                    .nominationEntity(nomination)
                    .accountEntity(account)
                    .likes(vote.getVotes())
                    .date(date)
                    .build();

            nominationHistoryEntities.add(entity);
        }

        nominationHistoryRepository.saveAll(nominationHistoryEntities);
    }

    @Override
    public List<NominationHistoryResponse> getNominationsHistory(Date date) {

        var histories = nominationHistoryRepository.findAll(date);

        return histories
                .stream()
                .map(dataMapper::bestTeacherHistoryEntityToNominationHistoryResponse)
                .toList();
    }

    @Override
    public List<TeacherNominationHistoryResponse> getTeacherNominationHistory(UUID accountId) {

        var histories = nominationHistoryRepository.findByAccountId(accountId);

        return histories
                .stream()
                .map(dataMapper::bestTeacherHistoryEntityToTeacherNominationHistoryResponse)
                .toList();
    }

    @Override
    public List<DDLResponse<Date>> getDatesDDL() {
        var dates = nominationHistoryRepository.getDatesDDL();
        ArrayList<DDLResponse<Date>> result = new ArrayList<>();

        for (Date date : dates) {
            String dateFormat = new SimpleDateFormat("yyyy:MM:dd").format(date);
            result.add(new DDLResponse<Date>(date, dateFormat));
        }

        return result;
    }

}
