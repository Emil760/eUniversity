package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.ThemeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Integer> {

    //TODO refactor
    //List<ThemeEntity> findAllByDisciplinePlanEntity(DisciplinePlanEntity disciplinePlanEntity);

    @Query(value = "select t " +
            "from ThemeEntity t " +
            "inner join EduProcessEntity edu on edu.id = t.eduProcessEntity.id " +
            "where t.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and (t.name like %:search% or edu.name like %:search% or t.description like %:search%) " +
            "order by t.eduProcessEntity.id, t.order")
    Page<ThemeEntity> findByGroupAndDiscipline(int facultyDisciplineId,
                                               Pageable pageable,
                                               String search);

    @Query(value = "select t " +
            "from ThemeEntity t " +
            "where t.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and t.eduProcessEntity.id = :eduProcessId " +
            "order by t.order")
    List<ThemeEntity> findByGroupAndDisciplineAndEduProcess(@Param("facultyDisciplineId") int facultyDisciplineId,
                                                            @Param("eduProcessId") int eduProcessId);

    @Query(value = "select coalesce(max(t.order), 0) " +
            "from ThemeEntity t " +
            "where t.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and t.eduProcessEntity.id = :eduProcessId")
    int getMaxOrder(@Param("facultyDisciplineId") int facultyDisciplineId,
                    @Param("eduProcessId") int eduProcessId);

    @Query(value = "select t " +
            "from ThemeEntity t " +
            "where t.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and t.eduProcessEntity.id = :eduProcessId " +
            "and t.name = :name")
    Optional<ThemeEntity> findByName(@Param("facultyDisciplineId") int facultyDisciplineId,
                                     @Param("eduProcessId") int eduProcessId,
                                     @Param("name") String name);

    @Query(value = "select t " +
            "from ThemeEntity t " +
            "where t.id != :id " +
            "and t.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and t.eduProcessEntity.id = :eduProcessId " +
            "and t.name = :name")
    Optional<ThemeEntity> findByNameExceptCurr(@Param("id") int id,
                                               @Param("facultyDisciplineId") int facultyDisciplineId,
                                               @Param("eduProcessId") int eduProcessId,
                                               @Param("name") String name);

    @Query(value = "select case when exists (" +
            " select * from themes where edu_process_id = :eduProcessId" +
            " )" +
            " then cast(1 as bit)" +
            " else cast(0 as bit)" +
            " end;", nativeQuery = true)
    Boolean isEduProcessPresent(@Param("eduProcessId") Integer eduProcessId);

}
