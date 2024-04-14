package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.FacultyDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.DegreeEntity;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import com.riverburg.eUniversity.model.entity.FacultyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyDisciplineRepository extends JpaRepository<FacultyDisciplineEntity, Integer> {

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.FacultyDisciplineResponse(fd.id, fd.disciplineEntity.name, fd.disciplineEntity.shortName, fd.semesterNumber, fd.isActive) " +
            "from FacultyDisciplineEntity fd " +
            "where fd.degreeEntity.id = :degreeId " +
            "and fd.facultyEntity.id = :facultyId " +
            "and (fd.disciplineEntity.name like %:search% or fd.disciplineEntity.shortName like %:search%) " +
            "and (:active = -1 " +
            "    or fd.isActive = cast(:active as boolean))")
    Page<FacultyDisciplineResponse> getDisciplines(Pageable pageable,
                                                   @Param("degreeId") Integer degreeId,
                                                   @Param("facultyId") Integer facultyId,
                                                   @Param("active") Integer active,
                                                   @Param("search") String search);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(fd.id, fd.disciplineEntity.name) " +
            "from FacultyDisciplineEntity  fd " +
            "where fd.facultyEntity.id = :facultyId " +
            "and fd.degreeEntity.id = :degreeId " +
            "and fd.isActive = true ")
    List<DDLResponse<Integer>> getActiveDisciplinesDDL(@Param("facultyId") Integer facultyId,
                                                       @Param("degreeId") Integer degreeId);

    @Query(value = "select fd " +
            "from FacultyDisciplineEntity fd " +
            "where fd.degreeEntity.id = :degreeId " +
            "and fd.facultyEntity.id = :facultyId " +
            "and fd.disciplineEntity.id = :disciplineId")
    Optional<FacultyDisciplineEntity> findByDegreeAndFacultyAndDiscipline(@Param("degreeId") Integer degreeId,
                                                                          @Param("facultyId") Integer facultyId,
                                                                          @Param("disciplineId") Integer disciplineId);

    @Query(value = "select fd " +
            "from FacultyDisciplineEntity fd " +
            "where fd.id = :id and fd.isActive = :isActive")
    Optional<FacultyDisciplineEntity> findByIdAndActive(@Param("id") Integer id,
                                                        @Param("isActive") Boolean isActive);

    @Query(value = "select fd " +
            "from FacultyDisciplineEntity fd " +
            "where fd.facultyEntity.id = :facultyId")
    List<FacultyDisciplineEntity> findByFacultyId(@Param("facultyId") Integer facultyId);

    @Query(value = "select fd " +
            "from FacultyDisciplineEntity fd " +
            "where fd.disciplineEntity.id = :disciplineId")
    List<FacultyDisciplineEntity> findByDisciplineId(@Param("disciplineId") Integer disciplineId);


    @Query(value = "select fd " +
            "from FacultyDisciplineEntity fd " +
            "where fd.degreeEntity.id = :degreeId")
    List<FacultyDisciplineEntity> findByDegreeId(@Param("degreeId") Integer degreeId);

    Optional<FacultyDisciplineEntity> findAllByFacultyEntity_IdAndIsActive(Integer facultyId, Boolean isActive);

    Optional<FacultyDisciplineEntity> findByFacultyEntity_IdAndDisciplineEntity_IdAndIsActive(Integer facultyId, Integer disciplineId, Boolean isActive);

    @Query(value = "select fd from FacultyDisciplineEntity fd " +
            "inner join FacultyEntity f on f = fd.facultyEntity " +
            "inner join DegreeEntity d on d = fd.degreeEntity " +
            "inner join GroupEntity g on g.facultyEntity = f and g.degreeEntity = d " +
            "where g.id = :groupId")
    List<FacultyDisciplineEntity> findAllByGroupId(@Param("groupId") Integer groupId);

    List<FacultyDisciplineEntity> findAllByFacultyEntityAndDegreeEntityAndSemesterNumber(FacultyEntity facultyEntity, DegreeEntity degreeEntity, Short semesterNumber);
}
