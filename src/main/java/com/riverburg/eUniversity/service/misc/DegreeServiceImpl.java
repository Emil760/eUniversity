package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.DegreeResponse;
import com.riverburg.eUniversity.model.entity.DegreeEntity;
import com.riverburg.eUniversity.repository.DegreeRepository;
import com.riverburg.eUniversity.repository.FacultyDisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegreeServiceImpl implements DegreeService {

    private final DegreeRepository degreeRepository;

    private final FacultyDisciplineRepository facultyDisciplineRepository;

    private final DataMapper dataMapper;

    DegreeServiceImpl(DegreeRepository degreeRepository,
                      FacultyDisciplineRepository facultyDisciplineRepository,
                      DataMapper dataMapper) {
        this.degreeRepository = degreeRepository;
        this.facultyDisciplineRepository = facultyDisciplineRepository;
        this.dataMapper = dataMapper;
    }

    @Override
    public List<DegreeResponse> getAllDegrees() {
        var degrees = degreeRepository.findAll();

        return degrees
                .stream()
                .map(dataMapper::degreeEntityToDegreeResponse)
                .toList();
    }

    @Override
    public List<DDLResponse<Integer>> getDegreesDDL() {
        return degreeRepository.findAllDDL();
    }

    @Override
    public void addDegree(String name) throws RestException {
        validateDegree(name);

        DegreeEntity degreeEntity = new DegreeEntity();

        degreeEntity.setName(name);

        degreeRepository.save(degreeEntity);
    }

    @Override
    public void deleteDegree(int id) throws RestException {
        findById(id);

        if (!facultyDisciplineRepository.findByDegreeId(id).isEmpty())
            throw RestException.of(ErrorConstant.DEGREE_USED);

        degreeRepository.deleteById(id);
    }

    @Override
    public DegreeEntity findById(Integer id) {
        return degreeRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.DEGREE_NOT_FOUND);
                });
    }

    private void validateDegree(String name) {
        degreeRepository.findByName(name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }

}
