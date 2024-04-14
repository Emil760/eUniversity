package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.EduProcessResponse;
import com.riverburg.eUniversity.model.entity.EduProcessEntity;
import com.riverburg.eUniversity.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EduProcessImpl implements EduProcessService {

    private final EduProcessRepository eduProcessRepository;

    private final DisciplinePlanRepository disciplinePlanRepository;

    private final ScheduleRepository scheduleRepository;

    private final ThemeRepository themeRepository;

    private final RoomRepository roomRepository;

    private final DataMapper dataMapper;

    @Override
    public List<EduProcessResponse> getAllEduProcesses() {
        var processes = eduProcessRepository.findAll();

        return processes
                .stream()
                .map(dataMapper::eduProcessEntityToEduProcessResponse)
                .toList();
    }

    @Override
    public List<DDLResponse<Integer>> getEduProcessesDDL() {
        return eduProcessRepository.findAllDDL();
    }

    @Override
    public void addEduProcess(String name) {
        validateEduProcess(name);

        EduProcessEntity eduProcessEntity = new EduProcessEntity();

        eduProcessEntity.setName(name);

        eduProcessRepository.save(eduProcessEntity);
    }

    @Override
    public void deleteEduProcess(int id) {
        findById(id);

        if (disciplinePlanRepository.isEduProcessPresent(id))
            throw RestException.of(ErrorConstant.EDU_PROCESS_USED_BY_DISCIPLINE_PLAN);

        if (scheduleRepository.isEduProcessPresent(id))
            throw RestException.of(ErrorConstant.EDU_PROCESS_USED_BY_SCHEDULE);

        if (themeRepository.isEduProcessPresent(id))
            throw RestException.of(ErrorConstant.EDU_PROCESS_USED_BY_THEME);

        if (roomRepository.isEduProcessPresent(id))
            throw RestException.of(ErrorConstant.EDU_PROCESS_USED_BY_ROOM);

        eduProcessRepository.deleteById(id);
    }

    @Override
    public EduProcessEntity findById(Integer id) {
        return eduProcessRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.EDU_PROCESS_NOT_FOUND);
                });
    }

    private void validateEduProcess(String name) {
        eduProcessRepository.findByName(name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
