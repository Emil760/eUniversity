package com.riverburg.eUniversity.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.riverburg.eUniversity.model.dto.request.post.AddDisciplineRequest;
import com.riverburg.eUniversity.model.dto.request.post.AddFacultyRequest;
import com.riverburg.eUniversity.model.dto.response.*;
import com.riverburg.eUniversity.model.dto.response.admin.AdminResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.material.MaterialResponse;
import com.riverburg.eUniversity.model.dto.response.misc.AcademicDegreeResponse;
import com.riverburg.eUniversity.model.dto.response.misc.DegreeResponse;
import com.riverburg.eUniversity.model.dto.response.misc.EduProcessResponse;
import com.riverburg.eUniversity.model.dto.response.misc.SectorResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationHistoryResponse;
import com.riverburg.eUniversity.model.dto.response.nomination.NominationResponse;
import com.riverburg.eUniversity.model.dto.response.student.*;
import com.riverburg.eUniversity.model.dto.response.student.profile.StudentProfileInfoResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.*;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;
import java.util.Date;


@Mapper(componentModel = "spring")
public interface DataMapper {

    //Accounts
    @Mapping(target = "password", ignore = true)
    AccountEntity registrationRequestToAccountEntity(RegistrationRequest registrationRequest);

    @Mapping(source = "isPaid", target = "paid")
    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "accountEntity.login", target = "login")
    @Mapping(source = "accountEntity.fullName", target = "fullName")
    @Mapping(source = "accountEntity.isActive", target = "active")
    @Mapping(source = "accountEntity.age", target = "age")
    @Mapping(source = "groupEntity.id", target = "groupId")
    @Mapping(source = "groupEntity.name", target = "groupName")
    @Mapping(source = "accountEntity.mail", target = "mail")
    StudentResponse studentEntityToStudentResponse(StudentEntity entity);

    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "accountEntity.login", target = "login")
    @Mapping(source = "accountEntity.fullName", target = "fullName")
    @Mapping(source = "accountEntity.isActive", target = "active")
    @Mapping(source = "accountEntity.age", target = "age")
    @Mapping(source = "academicDegreeEntity.id", target = "academicDegreeId")
    @Mapping(source = "academicDegreeEntity.name", target = "academicDegreeName")
    @Mapping(source = "accountEntity.mail", target = "mail")
    TeacherResponse teacherEntityToTeacherResponse(TeacherEntity entity);

    @Mapping(source = "accountEntity.id", target = "accountId")
    @Mapping(source = "accountEntity.login", target = "login")
    @Mapping(source = "accountEntity.fullName", target = "fullName")
    @Mapping(source = "accountEntity.isActive", target = "active")
    @Mapping(source = "accountEntity.age", target = "age")
    @Mapping(source = "accountEntity.mail", target = "mail")
    AdminResponse adminEntityToBaseUserResponse(AdminEntity entity);

    //Faculty
    @Mapping(source = "isActive", target = "active")
    FacultyResponse facultyEntityToFacultyResponse(FacultyEntity entity);

    FacultyEntity facultyRequestToFacultyEntity(AddFacultyRequest request);

    //PaidFaculty
    @Mapping(source = "degreeEntity.name", target = "degreeName")
    @Mapping(source = "facultyEntity.name", target = "facultyName")
    @Mapping(source = "degreeEntity.id", target = "degreeId")
    @Mapping(source = "facultyEntity.id", target = "facultyId")
    PaidFacultyResponse paidFacultyEntityToPaidFacultyResponse(PaidFacultyEntity entity);

    @Mapping(source = "groupEntity.name", target = "groupName")
    @Mapping(source = "accountEntity.fullName", target = "fullName")
    @Mapping(source = "isPaid", target = "paid")
    PaidFacultyStudentResponse studentEntityToPaidFacultyStudentResponse(StudentEntity entity);

    //Discipline
    @Mapping(source = "isActive", target = "active")
    DisciplineResponse disciplineEntityToDisciplineResponse(DisciplineEntity entity);

    DisciplineEntity disciplineRequestToDisciplineEntity(AddDisciplineRequest request);

    //Sector
    SectorResponse sectorEntityToSectorResponse(SectorEntity entity);

    //Degree
    DegreeResponse degreeEntityToDegreeResponse(DegreeEntity entity);

    //EduProcess
    EduProcessResponse eduProcessEntityToEduProcessResponse(EduProcessEntity entity);

    //AcademicDegree
    AcademicDegreeResponse academicDegreeEntityToAcademicDegreeResponse(AcademicDegreeEntity entity);

    //Rooms
    @Mapping(source = "isActive", target = "active")
    @Mapping(source = "eduProcessEntity.name", target = "eduProcessName")
    @Mapping(source = "eduProcessEntity.id", target = "eduProcessId")
    RoomResponse roomEntityToRoomResponse(RoomEntity entity);

    //Materials
    @Mapping(source = "fileEntity.originalFileName", target = "fileName")
    @Mapping(source = "fileEntity.accountEntity.id", target = "accountId")
    MaterialResponse materialEntityToMaterialResponse(MaterialEntity entity);

    @Mapping(source = "fileEntity.originalFileName", target = "fileName")
    @Mapping(source = "eduProcessEntity.name", target = "eduProcessName")
    GroupMaterialResponse groupMaterialEntityToGroupMaterialResponse(GroupMaterialEntity entity);

    //Theme
    @Mapping(source = "fileEntity.id", target = "fileId")
    @Mapping(source = "fileEntity.originalFileName", target = "fileName")
    @Mapping(source = "eduProcessEntity.name", target = "eduProcessName")
    ThemeResponse themeEntityToThemeResponse(ThemeEntity entity);

    //Nomination
    NominationResponse bestTeacherNominationEntityToNominationResponse(NominationEntity entity);

    @Mapping(source = "accountEntity.fullName", target = "teacherName")
    @Mapping(source = "nominationEntity.name", target = "nominationName")
    NominationHistoryResponse bestTeacherHistoryEntityToNominationHistoryResponse(NominationHistoryEntity entity);

    @Mapping(source = "nominationEntity.name", target = "nominationName")
    TeacherNominationHistoryResponse bestTeacherHistoryEntityToTeacherNominationHistoryResponse(NominationHistoryEntity entity);

    //Teacher
    @Mapping(source = "id", target = "journalId")
    @Mapping(source = "studentEntity.accountEntity.fullName", target = "studentName")
    JournalStudentResponse journalEntityToJournalStudentResponse_Activated(JournalEntity entity);

    @Mapping(source = "teacherEntity.id", target = "id")
    @Mapping(source = "teacherEntity.accountEntity.fullName", target = "name")
    DDLResponse<Integer> teachersDisciplineEntityToDDL(TeachersDisciplineEntity entity);

    @Mapping(source = "id", target = "scheduleId")
    @Mapping(source = "groupEntity.name", target = "groupName")
    TeacherScheduleResponse scheduleEntityToTeacherScheduleResponse(ScheduleEntity entity);

    @Mapping(source = "facultyDisciplineEntity.disciplineEntity.name", target = "disciplineName")
    @Mapping(source = "roomEntity.number", target = "cabinet")
    @Mapping(source = "eduProcessEntity.name", target = "eduProcess")
    TeacherScheduleDetailResponse scheduleEntityToTeacherScheduleDetailResponse(ScheduleEntity entity);

    @Mapping(target = "journalId", ignore = true)
    @Mapping(source = "studentEntity.accountEntity.fullName", target = "studentName")
    JournalStudentResponse journalEntityToJournalStudentResponse_NotActivated(JournalEntity entity);

    //Student
    @Mapping(source = "id", target = "scheduleId")
    @Mapping(source = "facultyDisciplineEntity.disciplineEntity.name", target = "disciplineName")
    StudentScheduleResponse scheduleEntityToStudentScheduleResponse(ScheduleEntity entity);

    @Mapping(source = "teacherEntity.accountEntity.fullName", target = "teacherName")
    @Mapping(source = "facultyDisciplineEntity.disciplineEntity.name", target = "disciplineName")
    @Mapping(source = "roomEntity.number", target = "cabinet")
    @Mapping(source = "eduProcessEntity.name", target = "eduProcess")
    StudentScheduleDetailResponse scheduleEntityToStudentScheduleDetailResponse(ScheduleEntity entity);

    @Mapping(source = "id", target = "studentId")
    @Mapping(source = "groupEntity.id", target = "groupId")
    @Mapping(source = "groupEntity.facultyEntity.id", target = "facultyId")
    @Mapping(source = "groupEntity.degreeEntity.id", target = "degreeId")
    @Mapping(source = "groupEntity.sectorEntity.id", target = "sectorId")
    StudentInfoResponse studentEntityToStudentInfoResponse(StudentEntity entity);

    @Mapping(source = "accountEntity.fullName", target = "fullName")
    @Mapping(source = "accountEntity.age", target = "age")
    @Mapping(source = "ball", target = "score")
    @Mapping(source = "isPaid", target = "paid")
    @Mapping(source = "groupEntity.name", target = "groupName")
    @Mapping(source = "groupEntity.facultyEntity.name", target = "facultyName")
    @Mapping(source = "groupEntity.degreeEntity.name", target = "degreeName")
    @Mapping(source = "groupEntity.sectorEntity.name", target = "sectorName")
    @Mapping(source = "groupEntity.startDate", target = "startDate")
    @Mapping(source = "groupEntity.semester", target = "semesterNumber")
    StudentProfileInfoResponse studentEntityToStudentProfileInfoResponse(StudentEntity entity);
}
