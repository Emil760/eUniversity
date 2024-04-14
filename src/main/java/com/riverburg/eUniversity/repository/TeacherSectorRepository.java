package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorResponse;
import com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorsResponse;
import com.riverburg.eUniversity.model.entity.TeachersSectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherSectorRepository extends JpaRepository<TeachersSectorEntity, Integer> {

    @Query(value = "select ts " +
            "from TeachersSectorEntity ts " +
            "where ts.teacherEntity.id = :teacherId " +
            "and ts.sectorEntity.id = :sectorId")
    Optional<TeachersSectorEntity> findByTeacherAndSector(@Param("teacherId") Integer teacherId, @Param("sectorId") Integer sectorId);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.teacher.TeacherSectorResponse(ts.sectorEntity.id, ts.sectorEntity.name) " +
            "from TeachersSectorEntity  ts " +
            "where ts.teacherEntity.id = :teacherId")
    List<TeacherSectorResponse> findSectors(@Param("teacherId") Integer teacherId);

    @Query(value = "select ts " +
            "from TeachersSectorEntity ts " +
            "where ts.sectorEntity.id = :sectorId")
    List<TeachersSectorEntity> findBySector(@Param("sectorId") Integer sectorId);

    @Query(value = "SELECT t.id, " +
            "a.full_name AS teacherName, " +
            "(select STRING_AGG(s.name, ', ') from teachers_sectors ts inner join sectors s on ts.sector_id = s.id where ts.teacher_id = t.id group by ts.teacher_id) AS names " +
            "FROM teachers t " +
            "LEFT JOIN teachers_sectors ts ON ts.teacher_id = t.id " +
            "LEFT JOIN sectors s ON s.id = ts.sector_id " +
            "INNER JOIN accounts a ON a.id = t.account_id " +
            "WHERE (a.full_name LIKE %:search% OR s.name LIKE %:search%) " +
            "AND a.is_active = 1" +
            "GROUP BY t.id, a.full_name " +
            "ORDER BY t.id DESC " +
            "OFFSET (:pageIndex * :pageSize) ROWS FETCH NEXT :pageSize ROWS ONLY", nativeQuery = true)
    List<TeacherSectorsResponse> findTeachersSectors(@Param("pageIndex") Integer pageIndex,
                                                     @Param("pageSize") Integer pageSize,
                                                     @Param("search") String search);


    @Query(value = "SELECT COUNT_BIG(distinct t.id) " +
            "FROM teachers t " +
            "LEFT JOIN teachers_sectors ts ON ts.teacher_id = t.id " +
            "LEFT JOIN sectors s ON s.id = ts.sector_id " +
            "INNER JOIN accounts a ON a.id = t.account_id " +
            "WHERE (a.full_name LIKE %:search% OR s.name LIKE %:search%) " +
            "AND a.is_active = 1", nativeQuery = true)
    Long getTeachersSectorsCount(@Param("search") String search);
}
