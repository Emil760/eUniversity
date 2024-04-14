package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherProfileInfoResponse;
import com.riverburg.eUniversity.model.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

    Optional<TeacherEntity> findById(Integer id);

    @Query(value = "select t " +
            "from TeacherEntity t " +
            "where (t.accountEntity.fullName like %:search% " +
            "      or t.accountEntity.login like %:search% " +
            "      or t.academicDegreeEntity.name like %:search% " +
            "      or t.accountEntity.mail like %:search%) " +
            "and (:active = -1" +
            "     or t.accountEntity.isActive = cast(:active as boolean))")
    Page<TeacherEntity> findTeachers(Pageable pageable, String search, Integer active);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(t.id, t.accountEntity.fullName) " +
            "from TeacherEntity t " +
            "where t.accountEntity.isActive = true")
    List<DDLResponse<Integer>> getActiveTeachersDDL();

    @Query(value = "select t " +
            "from TeacherEntity t " +
            "where t.academicDegreeEntity.id = :academicDegreeId")
    List<TeacherEntity> findByAcademicDegree(@Param("academicDegreeId") Integer academicDegreeId);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(t.id, a.fullName) " +
            "from TeacherEntity t " +
            "inner join AccountEntity a on a.id = t.accountEntity.id " +
            "inner join TeachersSectorEntity ts on ts.teacherEntity.id = t.id " +
            "inner join TeachersDisciplineEntity td on td.teacherEntity.id = t.id " +
            "where ts.sectorEntity.id = :sectorId " +
            "and td.disciplineEntity.id = :disciplineId " +
            "and a.isActive = true")
    List<DDLResponse<Integer>> getTeacherDisciplineSector(@Param("disciplineId") Integer disciplineId,
                                                          @Param("sectorId") Integer sectorId);

    @Query(value = "select distinct new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(g.id, g.name) " +
            "from TeacherEntity t " +
            "inner join AccountEntity a on a.id = t.accountEntity.id " +
            "inner join ScheduleEntity sch on sch.teacherEntity.id = t.id " +
            "inner join GroupEntity g on g.id = sch.groupEntity.id " +
            "where a.id = :accountId")
    List<DDLResponse<Integer>> getTeacherGroupsDDL(@Param("accountId") UUID accountId);

    @Query(value = "select distinct new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(d.id, concat(d.name, ' (', d.shortName, ')')) " +
            "from TeacherEntity t " +
            "inner join AccountEntity a on a.id = t.accountEntity.id " +
            "inner join ScheduleEntity sch on sch.teacherEntity.id = t.id " +
            "inner join GroupEntity g on g.id = sch.groupEntity.id " +
            "inner join FacultyDisciplineEntity fd on fd.id = sch.facultyDisciplineEntity.id " +
            "inner join DisciplineEntity  d on d.id = fd.disciplineEntity.id " +
            "where a.id = :accountId " +
            "and g.id = :groupId")
    List<DDLResponse<Integer>> getTeacherGroupsDisciplinesDDL(@Param("accountId") UUID accountId,
                                                              @Param("groupId") Integer groupId);



    @Query(value = "select " +
            "a.full_name as fullName, " +
            "a.age as age, " +
            "ad.name as academicDegreeName, " +
            "(select STRING_AGG(d.name, ', ') from teachers_disciplines td " +
            "                                 inner join disciplines d on d.id = td.discipline_id " +
            "                                 where td.teacher_id = t.id) as disciplines, " +
            "(select STRING_AGG(s.name, ', ') from teachers_sectors ts " +
            "                                 inner join sectors s on s.id = ts.sector_id " +
            "                                 where ts.teacher_id = t.id) as sectors " +
            "from teachers t " +
            "inner join accounts a on a.id = t.account_id " +
            "inner join academic_degree ad on ad.id = t.academic_degree_id " +
            "where a.id = :accountId ", nativeQuery = true)
    TeacherProfileInfoResponse getTeacherProfileInfo(@Param("accountId") String accountId);
}

