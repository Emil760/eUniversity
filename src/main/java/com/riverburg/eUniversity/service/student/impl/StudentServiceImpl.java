package com.riverburg.eUniversity.service.student.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.request.base.PaginationRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegisterStudentRequest;
import com.riverburg.eUniversity.model.dto.request.put.UpdateStudentRequest;
import com.riverburg.eUniversity.model.dto.response.base.PaginatedListResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileAttendanceResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.student.StudentResponse;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileStatsResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import com.riverburg.eUniversity.repository.user.StudentRepository;
import com.riverburg.eUniversity.service.faculty.PaidFacultyService;
import com.riverburg.eUniversity.service.group.GroupService;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.journal.JournalService;
import com.riverburg.eUniversity.service.security.AccountAuthService;
import com.riverburg.eUniversity.service.student.StudentScheduleService;
import com.riverburg.eUniversity.service.student.StudentService;
import com.riverburg.eUniversity.service.student.StudentStatsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentStatsService studentStatsService;

    private final AccountAuthService accountAuthService;

    private final AccountService accountService;

    private final GroupService groupService;

    private final PaidFacultyService paidFacultyService;

    private final StudentScheduleService studentScheduleService;

    private final JournalService journalService;

    private final DataMapper dataMapper;

    @Override
    public void register(RegisterStudentRequest request) {
        AccountEntity account = accountAuthService.register(request, Role.STUDENT);

        StudentEntity student = new StudentEntity();
        student.setBall(request.getBall());
        student.setAccountEntity(account);

        studentRepository.save(student);
    }

    @Override
    public PaginatedListResponse<StudentResponse> getStudent(PaginationRequest pagination, int groupId, int active) {
        Pageable pageable = PageRequest.of(pagination.getPageIndex(), pagination.getPageSize());

        var studentsPage = studentRepository.findStudents(pageable, pagination.getSearch(), groupId, active);

        var students = studentsPage
                .getContent()
                .stream()
                .map(dataMapper::studentEntityToStudentResponse)
                .toList();

        var itemsCount = studentsPage.getTotalElements();

        return new PaginatedListResponse<StudentResponse>(students, itemsCount);
    }

    @Override
    public StudentProfileInfoResponse getStudentProfileInfo(UUID accountId) {
        var student = Optional
                .ofNullable(studentRepository.getStudentByAccountId(accountId))
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        return dataMapper.studentEntityToStudentProfileInfoResponse(student);
    }

    @Override
    public StudentProfileStatsResponse getStudentProfileStatsInfo(UUID accountId) {
        var student = Optional
                .ofNullable(studentRepository.getStudentByAccountId(accountId))
                .orElseThrow(() -> RestException.of(ErrorConstant.STUDENT_NOT_FOUND));

        return studentStatsService.getStudentProfileStats(student);
    }

    @Override
    public List<StudentProfileAttendanceResponse> getStudentProfileAttendance(UUID accountId, short semester) {
        return studentScheduleService
                .getSchedules(accountId, semester)
                .stream()
                .map(s -> StudentProfileAttendanceResponse.
                        builder()
                        .disciplineSchedule(s)
                        .attendances(
                                journalService
                                        .findAllAttendancesByStudentAccId(accountId, s.getScheduleId())
                                        .stream()
                                        .map(JournalEntity::getDate)
                                        .toList()
                        )
                        .build())
                .toList();
    }

    @Override
    public StudentInfoResponse getStudentInfo(UUID accountId) {
        var student = studentRepository.getStudentByAccountId(accountId);

        return dataMapper.studentEntityToStudentInfoResponse(student);
    }

    @Override
    public void updateStudent(UpdateStudentRequest request) {
        var account = accountService.findById(request.getAccountId());
        account.setFullName(request.getFullName());
        account.setMail(request.getMail());
        account.setAge(request.getAge());

        var student = findById(request.getId());
        student.setBall(request.getBall());
        student.setAccountEntity(account);

        try {
            var group = groupService.findById(request.getGroupId());
            student.setGroupEntity(group);
            studentRepository.save(student);

            try {
                var paidFaculty = paidFacultyService.findByFacultyAndDegreeAndYear(
                        (short) group.getStartDate().getYear(),
                        group.getFacultyEntity().getId(),
                        group.getDegreeEntity().getId());

                paidFacultyService.sortPaidStudents(
                        (short) group.getStartDate().getYear(),
                        group.getFacultyEntity().getId(),
                        group.getDegreeEntity().getId(),
                        paidFaculty.getFreeCount());

            } catch (RestException exception) {
                student.setIsPaid(true);
                studentRepository.save(student);
            }

        } catch (RestException ignored) {
        }
    }

    @Override
    public StudentEntity findById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.STUDENT_NOT_FOUND);
                });
    }

    @Override
    public StudentEntity findByAccountId(UUID accountId) {
        return studentRepository.findByAccountEntityId(accountId)
                .orElseThrow(() -> RestException.of(ErrorConstant.ACCOUNT_NOT_FOUND));
    }
}
