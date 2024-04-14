package com.riverburg.eUniversity.service.bestteacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationResponse;
import com.riverburg.eUniversity.model.entity.NominationEntity;
import com.riverburg.eUniversity.repository.NominationRepository;
import com.riverburg.eUniversity.service.configuration.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NominationServiceImpl implements NominationService {

    private final NominationRepository nominationRepository;

    private final ConfigurationService configurationService;

    private final DataMapper dataMapper;

    @Override
    public List<NominationResponse> getNominations() {
        var nominations = nominationRepository.findAll();

        return nominations
                .stream()
                .map(dataMapper::bestTeacherNominationEntityToNominationResponse)
                .toList();
    }

    @Override
    public List<Integer> getActiveNominationIds() {
        return nominationRepository.getActiveNominationIds();
    }

    @Override
    public List<DDLResponse<Integer>> getNominationsDDL() {
        return nominationRepository.getNominationsDDL();
    }

    @Override
    public void addNomination(String name) {
        validateAddNomination(name);

        NominationEntity nominationEntity = NominationEntity
                .builder()
                .name(name)
                .build();

        nominationRepository.save(nominationEntity);
    }

    @Override
    public void activateNomination(int id, boolean isActive) {
        NominationEntity nominationEntity = findById(id);

        nominationEntity.setIsActive(isActive);

        nominationRepository.save(nominationEntity);
    }

    @Override
    public NominationEntity findById(int id) throws RestException {
        return nominationRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.NOMINATION_NOT_FOUND);
                });
    }

    @Override
    public void startNomination() {
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        configurationService.addDateConfig("NOMINATION_START", date, "yyyy-MM-dd");
    }

    @Override
    public void stopNomination() {
        configurationService.addDateConfig("NOMINATION_START", null, "");
    }

    private void validateAddNomination(String name) {
        nominationRepository.findByName(name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
