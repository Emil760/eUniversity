package com.riverburg.eUniversity.repository;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.riverburg.eUniversity.model.dto.response.ScheduleResponse;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import com.riverburg.eUniversity.model.entity.GroupEntity;
import com.riverburg.eUniversity.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {

    @Query(value = "select sch " +
            "from ScheduleEntity sch " +
            "inner join TeacherEntity t on t.id = sch.teacherEntity.id " +
            "inner join AccountEntity a on a.id = t.accountEntity.id " +
            "inner join FacultyDisciplineEntity fd on fd.id = sch.facultyDisciplineEntity.id " +
            "where a.id = :accountId " +
            "and sch.groupEntity.id = :groupId " +
            "and fd.disciplineEntity.id = :disciplineId")
    List<ScheduleEntity> getTeacherSchedules(@Param("accountId") UUID accountId,
                                             @Param("groupId") Integer groupId,
                                             @Param("disciplineId") Integer disciplineId);

    @Query(value = "select sch " +
            "from ScheduleEntity sch " +
            "inner join TeacherEntity t on t.id = sch.teacherEntity.id " +
            "inner join AccountEntity a on a.id = t.accountEntity.id " +
            "where a.id = :accountId")
    List<ScheduleEntity> getTeacherSchedules(@Param("accountId") UUID accountId);

    @Query(value = "select sch " +
            "from ScheduleEntity sch " +
            "inner join FacultyDisciplineEntity fd on fd.id = sch.facultyDisciplineEntity.id " +
            "inner join GroupEntity g on g.id = sch.groupEntity.id " +
            "inner join StudentEntity s on s.groupEntity.id = g.id " +
            "inner join AccountEntity a on a.id = s.accountEntity.id " +
            "where a.id = :accountId " +
            "and fd.semesterNumber = :semester")
    List<ScheduleEntity> getStudentsSchedules(@Param("accountId") UUID accountId,
                                              @Param("semester") Short semester);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.ScheduleResponse" +
            "(sch.id, fd.id, d.id, d.name, t.id, a.fullName, a.isActive, edu.id, edu.name, sch.type, sch.beginDate, sch.endDate, sch.from, sch.to, room.id, room.number, sch.isActive) " +
            "from GroupEntity g " +
            "inner join FacultyDisciplineEntity fd on fd.facultyEntity.id = g.facultyEntity.id and fd.degreeEntity.id = g.degreeEntity.id " +
            "inner join DisciplineEntity d on d.id = fd.disciplineEntity.id " +
            "left join ScheduleEntity sch on sch.groupEntity.id = g.id and sch.facultyDisciplineEntity.id = fd.id " +
            "left join EduProcessEntity edu on edu.id = sch.eduProcessEntity.id " +
            "left join RoomEntity room on room.id = sch.roomEntity.id " +
            "left join TeacherEntity t on t.id = sch.teacherEntity.id " +
            "left join AccountEntity a on a.id = t.accountEntity.id " +
            "where g.id = :groupId " +
            "and fd.semesterNumber = :semesterNumber " +
            "and fd.isActive = true " +
            "order by fd.id")
    List<ScheduleResponse> getScheduleList(@Param("groupId") Integer groupId,
                                           @Param("semesterNumber") Short semesterNumber);

    @Query(value = "select sch " +
            "from ScheduleEntity sch " +
            "where sch.roomEntity.id = :roomId")
    List<ScheduleEntity> findByRoomId(@Param("roomId") Integer roomId);

    List<ScheduleEntity> findAllByFacultyDisciplineEntityAndGroupEntity(FacultyDisciplineEntity facultyDisciplineEntity,
                                                                        GroupEntity groupEntity);

    @Query(value = "select case when exists (" +
            " select * from schedule where edu_process_id = :eduProcessId" +
            " )" +
            " then cast(1 as bit)" +
            " else cast(0 as bit)" +
            " end;", nativeQuery = true)
    Boolean isEduProcessPresent(@Param("eduProcessId") Integer eduProcessId);

}