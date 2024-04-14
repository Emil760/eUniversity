package com.riverburg.eUniversity.service.misc;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.misc.AcademicDegreeResponse;
import com.riverburg.eUniversity.model.entity.AcademicDegreeEntity;
import com.riverburg.eUniversity.repository.AcademicDegreeRepository;
import com.riverburg.eUniversity.repository.user.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AcademicDegreeServiceImpl implements AcademicDegreeService {

    private final AcademicDegreeRepository academicDegreeRepository;

    private final TeacherRepository teacherRepository;

    private final DataMapper dataMapper;

    @Override
    public List<AcademicDegreeResponse> getAllDegrees() {
        var degrees = academicDegreeRepository.findAll();

        return degrees
                .stream()
                .map(dataMapper::academicDegreeEntityToAcademicDegreeResponse)
                .toList();
    }

    @Override
    public List<DDLResponse<Integer>> getDegreesDDL() {
        return academicDegreeRepository.findAllDDL();
    }

    @Override
    public void addDegree(String name) {
        validateDegree(name);

        AcademicDegreeEntity entity = new AcademicDegreeEntity();

        entity.setName(name);

        academicDegreeRepository.save(entity);
    }

    @Override
    public void deleteDegree(int id) throws RestException {
        var degree = findById(id);

        if (!teacherRepository.findByAcademicDegree(id).isEmpty())
            throw RestException.of(ErrorConstant.ACADEMIC_DEGREE_USED_BY_TEACHER);

        academicDegreeRepository.delete(degree);
    }

    @Override
    public AcademicDegreeEntity findById(Integer id) {
        return academicDegreeRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ACADEMIC_DEGREE_NOT_FOUND);
                });
    }

    private void validateDegree(String name) {
        if (name == null || name.isBlank())
            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, "Name is empty");

        academicDegreeRepository.findByName(name)
                .ifPresent(f -> {
                    throw RestException.of(ErrorConstant.UNIQUE_VALIDATION);
                });
    }
}
