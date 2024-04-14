package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse;
import com.riverburg.eUniversity.model.entity.DisciplinePlanEntity;
import com.riverburg.eUniversity.model.entity.FacultyDisciplineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisciplinePlanRepository extends JpaRepository<DisciplinePlanEntity, Integer> {

    @Query(value = "select dp " +
            "from DisciplinePlanEntity dp " +
            "where dp.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and dp.eduProcessEntity.id = :eduProcessId")
    Optional<DisciplinePlanEntity> findByFacultyDisciplineAndDegree(@Param("facultyDisciplineId") Integer facultyDisciplineId,
                                                                    @Param("eduProcessId") Integer eduProcessId);
    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.DisciplinePlanResponse(dp.id, dp.eduProcessEntity.name, dp.count, dp.grade) " +
            "from DisciplinePlanEntity dp " +
            "where dp.facultyDisciplineEntity.id = :facultyDisciplineId")
    List<DisciplinePlanResponse> findAll(@Param("facultyDisciplineId") Integer facultyDisciplineId);

    @Query(value = "select case when exists (" +
            " select * from disciplines_plan where edu_process_id = :eduProcessId" +
            " )" +
            " then cast(1 as bit)" +
            " else cast(0 as bit)" +
            " end;", nativeQuery = true)
    Boolean isEduProcessPresent(@Param("eduProcessId") Integer eduProcessId);

    Optional<DisciplinePlanEntity> findByFacultyDisciplineEntityAndEduProcessEntityId(FacultyDisciplineEntity facultyDisciplineEntities, Integer eduProcessEntityId);
}
