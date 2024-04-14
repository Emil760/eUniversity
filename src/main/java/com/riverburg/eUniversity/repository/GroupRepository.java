package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.dto.response.GroupResponse;
import com.riverburg.eUniversity.model.entity.GroupEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import com.riverburg.eUniversity.model.entity.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    Optional<GroupEntity> findByName(String name);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.GroupResponse(g.id, g.name, g.startDate, g.facultyEntity.name, g.sectorEntity.name, count(s.id), g.semester) " +
            "from GroupEntity g " +
            "left join StudentEntity s on s.groupEntity.id = g.id " +
            "where g.degreeEntity.id = :degreeId " +
            "and (g.name like %:search% or g.facultyEntity.name like %:search%) " +
            "group by g.id, g.name, g.startDate, g.facultyEntity.name, g.sectorEntity.name, g.semester   ")
    Page<GroupResponse> findByDegreeGroups(Pageable pageable,
                                           @Param("degreeId") Integer degreeId,
                                           @Param("search") String search);

    @Query(value = "select g " +
            "from GroupEntity g " +
            "where g.sectorEntity.id = :sectorId")
    List<GroupEntity> findBySector(@Param("sectorId") Integer sectorId);

    @Query(value = "select g " +
            "from GroupEntity g " +
            "where g.facultyEntity.id = :facultyId")
    List<GroupEntity> findByFaculty(@Param("facultyId") Integer facultyId);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(g.id, g.name) " +
            "from GroupEntity g")
    List<DDLResponse<Integer>> getGroupsDDL();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(g.id, concat(g.name, ' (', f.shortName, ')') ) " +
            "from GroupEntity g " +
            "inner join FacultyEntity f on f.id = g.facultyEntity.id ")
    List<DDLResponse<Integer>> getGroupsWithFacultiesDDL();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(g.id, concat(g.name, ' (', d.name, ')') ) " +
            "from GroupEntity g " +
            "inner join DegreeEntity d on d.id = g.degreeEntity.id")
    List<DDLResponse<Integer>> getGroupsWithDegreesDDL();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(fd.id, d.name) " +
            "from GroupEntity g " +
            "inner join FacultyDisciplineEntity fd on fd.facultyEntity.id = g.facultyEntity.id and fd.degreeEntity.id = g.degreeEntity.id " +
            "inner join DisciplineEntity  d on d.id = fd.disciplineEntity.id " +
            "where g.id = :groupId " +
            "and fd.isActive = true")
    List<DDLResponse<Integer>> getGroupsDisciplinesDDL(Integer groupId);

    @Query(value = "select count(s.id) " +
            "from GroupEntity g " +
            "inner join StudentEntity s on s.groupEntity.id = g.id " +
            "where g.facultyEntity.id = :facultyId " +
            "and g.degreeEntity.id = :degreeId " +
            "and cast(year(g.startDate) as short ) = :year")
    Integer getStudentCountByFacultyAndDegree(@Param("year") Short year,
                                              @Param("facultyId") Integer facultyId,
                                              @Param("degreeId") Integer degreeId);

    @Query(value = "select s " +
            "from StudentEntity s " +
            "inner join GroupEntity g on g.id = s.groupEntity.id " +
            "where g.facultyEntity.id = :facultyId " +
            "and g.degreeEntity.id = :degreeId " +
            "and cast(year(g.startDate) as short) = :year " +
            "order by s.ball desc")
    List<StudentEntity> findStudentsByFacultyAndDegree(@Param("year") Short year,
                                                       @Param("facultyId") Integer facultyId,
                                                       @Param("degreeId") Integer degreeId);

    @Query(value = "select g.facultyEntity.semesterCount " +
            "from GroupEntity g " +
            "where g.id = :groupId ")
    short getSemesterCount(@Param("groupId") Integer groupId);

    @Query(value = "select distinct t from GroupEntity g " +
            "inner join ScheduleEntity s on g = s.groupEntity " +
            "inner join TeacherEntity t on t = s.teacherEntity " +
            "where g.id = :groupId ")
    List<TeacherEntity> findTeachersByGroupId(@Param("groupId") Integer groupId);

    @Query(value = "select s from StudentEntity s " +
            "inner join GroupEntity g on g = s.groupEntity " +
            "where g.id = :groupId")
    List<StudentEntity> findStudentsByGroupId(@Param("groupId") Integer groupId);

}
