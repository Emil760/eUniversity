package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import com.riverburg.eUniversity.model.entity.JournalEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface JournalRepository extends JpaRepository<JournalEntity, Integer> {

    @Query(value = "select j " +
            "from StudentEntity s " +
            "left join JournalEntity j on j.studentEntity.id = s.id " +
            "left join ScheduleEntity sch on sch.id = j.scheduleEntity.id " +
            "left join GroupEntity g on g.id = sch.groupEntity.id " +
            "where g.id = sch.groupEntity.id " +
            "and sch.id = :scheduleId")
    List<JournalEntity> getJournalStudents(@Param("scheduleId") Integer scheduleId);

    @Query(value = "select j " +
            "from JournalEntity j " +
            "inner join StudentEntity s on s.id = j.studentEntity.id " +
            "inner join AccountEntity a on a.id = s.accountEntity.id " +
            "inner join ScheduleEntity sch on sch.id = j.scheduleEntity.id " +
            "where sch.id = :scheduleId " +
            "and cast(j.date as date) = cast(:date as date) " +
            "and cast(:date as time) > cast(sch.from as time) " +
            "order by a.fullName")
    List<JournalEntity> getJournalActivatedStudents(@Param("scheduleId") Integer scheduleId,
                                                    @Param("date") Date date);

    List<JournalEntity> getAllByStudentEntityAndScheduleEntityFacultyDisciplineEntityAndGradeIsNotNull(StudentEntity studentEntity, FacultyDisciplineEntity facultyDisciplineEntity);

    List<JournalEntity> getAllByStudentEntityAndGradeIsNotNull(StudentEntity studentEntity);

    int countByAttendanceIsTrueAndScheduleEntity_FacultyDisciplineEntity(FacultyDisciplineEntity facultyDisciplineEntity);

    int countByAttendanceIsFalseAndScheduleEntity_FacultyDisciplineEntity(FacultyDisciplineEntity facultyDisciplineEntity);

    @Query(value = "select j " +
            "from JournalEntity j " +
            "inner join StudentEntity s on s.id = j.studentEntity.id " +
            "inner join AccountEntity a on a.id = s.accountEntity.id " +
            "inner join ScheduleEntity sch on sch.id = j.scheduleEntity.id " +
            "where a.id = :accountId and sch.id = :scheduleId and j.attendance = true " +
            "order by j.date")
    List<JournalEntity> getAllIsAttendedByStudentAccountIdAndScheduleId(@Param("accountId") UUID accountId,
                                                                        @Param("scheduleId") Integer scheduleId);
}
