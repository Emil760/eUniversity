package com.riverburg.eUniversity.repository;


import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplineResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplinesResponse;
import com.riverburg.eUniversity.model.entity.TeachersDisciplineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeacherDisciplineRepository extends JpaRepository<TeachersDisciplineEntity, Integer> {

    @Query(value = "select td " +
            "from TeachersDisciplineEntity td " +
            "where td.teacherEntity.id = :teacherId " +
            "and td.disciplineEntity.id = :disciplineId")
    Optional<TeachersDisciplineEntity> findByTeacherAndDiscipline(@Param("teacherId") Integer teacherId,
                                                                  @Param("disciplineId") Integer disciplineId);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.teacher.TeacherDisciplineResponse(td.disciplineEntity.id, td.disciplineEntity.name) " +
            "from TeachersDisciplineEntity  td " +
            "where td.teacherEntity.id = :teacherId")
    List<TeacherDisciplineResponse> findDisciplines(@Param("teacherId") Integer teacherId);

    @Query(value = "select td " +
            "from TeachersDisciplineEntity td " +
            "where td.disciplineEntity.id = :disciplineId")
    List<TeachersDisciplineEntity> findByDiscipline(@Param("disciplineId") Integer disciplineId);

    @Query(value = "SELECT t.id, " +
            "a.full_name AS teacherName, " +
            "(select STRING_AGG(d.name, ', ') from teachers_disciplines td inner join disciplines d on td.discipline_id = d.id where td.teacher_id = t.id group by td.teacher_id) AS names, " +
            "(select STRING_AGG(d.short_name, ', ') from teachers_disciplines td inner join disciplines d on td.discipline_id = d.id where td.teacher_id = t.id group by td.teacher_id) AS shortNames " +
            "FROM teachers t " +
            "LEFT JOIN teachers_disciplines td ON td.teacher_id = t.id " +
            "LEFT JOIN disciplines d ON d.id = td.discipline_id " +
            "INNER JOIN accounts a ON a.id = t.account_id " +
            "WHERE (a.full_name LIKE %:search% OR d.name LIKE %:search% OR d.short_name LIKE %:search%) " +
            "AND a.is_active = 1" +
            "GROUP BY t.id, a.full_name " +
            "ORDER BY t.id DESC " +
            "OFFSET (:pageIndex * :pageSize) ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
    List<TeacherDisciplinesResponse> findTeachersDisciplines(@Param("pageIndex") Integer pageIndex,
                                                             @Param("pageSize") Integer pageSize,
                                                             @Param("search") String search);

    @Query(value = "SELECT COUNT_BIG(distinct t.id) " +
            "FROM teachers t " +
            "LEFT JOIN teachers_disciplines td ON td.teacher_id = t.id " +
            "LEFT JOIN disciplines d ON d.id = td.discipline_id " +
            "INNER JOIN accounts a ON a.id = t.account_id " +
            "WHERE (a.full_name LIKE %:search% OR d.name LIKE %:search%) " +
            "AND a.is_active = 1 ", nativeQuery = true)
    Long getTeachersDisciplinesCount(@Param("search") String search);
}

