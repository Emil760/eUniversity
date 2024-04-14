package com.riverburg.eUniversity.service.teacher;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegisterTeacherRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateTeacherRequest;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherResponse;
import com.riverburg.eUniversity.model.entity.AcademicDegreeEntity;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.TeacherEntity;
import com.riverburg.eUniversity.repository.user.TeacherRepository;
import com.riverburg.eUniversity.service.misc.AcademicDegreeService;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.security.AccountAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final AccountService accountService;

    private final AccountAuthService accountAuthService;

    private final AcademicDegreeService academicDegreeService;

    private final DataMapper dataMapper;

    @Override
    public void register(RegisterTeacherRequest request) {
        AccountEntity account = accountAuthService.register(request, Role.TEACHER);

        AcademicDegreeEntity academicDegreeEntity = academicDegreeService.findById(request.getAcademicDegreeId());

        TeacherEntity teacher = new TeacherEntity();
        teacher.setAccountEntity(account);
        teacher.setAcademicDegreeEntity(academicDegreeEntity);

        teacherRepository.save(teacher);
    }

    @Override
    public PaginatedListResponse<TeacherResponse> getTeachers(PaginationRequest pagination, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var teachersPage = teacherRepository.findTeachers(pageable, pagination.getSearch(), active);

        var teachers = teachersPage
                .getContent()
                .stream()
                .map(dataMapper::teacherEntityToTeacherResponse)
                .toList();

        var itemsCount = teachersPage.getTotalElements();

        return new PaginatedListResponse<TeacherResponse>(teachers, itemsCount);
    }

    @Override
    public List<DDLResponse<Integer>> getActiveTeachersDDL() {
        return teacherRepository.getActiveTeachersDDL();
    }

    @Override
    public void update(UpdateTeacherRequest request) {
        var teacher = findById(request.getId());

        var academicDegree = academicDegreeService.findById(request.getAcademicDegreeId());

        teacher.setAcademicDegreeEntity(academicDegree);

        var account = accountService.findById(request.getAccountId());
        account.setAge(request.getAge());
        account.setFullName(request.getFullName());
        account.setMail(request.getMail());
        teacher.setAccountEntity(account);

        teacherRepository.save(teacher);
    }

    @Override
    public List<DDLResponse<Integer>> getTeacherDisciplineSector(int disciplineId, int sectorId) {
        return teacherRepository.getTeacherDisciplineSector(disciplineId, sectorId);
    }

    @Override
    public TeacherProfileInfoResponse getTeacherProfileInfo(UUID accountId) {
        return teacherRepository.getTeacherProfileInfo(accountId.toString());
    }

    @Override
    public TeacherEntity findById(int id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.TEACHER_NOT_FOUND);
                });
    }

}
