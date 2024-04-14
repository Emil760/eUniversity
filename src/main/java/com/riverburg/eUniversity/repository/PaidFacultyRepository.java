package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.PaidFacultyEntity;
import com.riverburg.eUniversity.model.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaidFacultyRepository extends JpaRepository<PaidFacultyEntity, Integer> {

    @Query(value = "select pf " +
            "from PaidFacultyEntity pf " +
            "where pf.facultyEntity.name like %:search% " +
            "and (:year = 0 or pf.year = :year) " +
            "order by pf.year")
    Page<PaidFacultyEntity> findPaidFaculties(Integer year, Pageable pageable, String search);

    @Query(value = "select pf " +
            "from PaidFacultyEntity pf " +
            "where pf.year = :year and pf.facultyEntity.id = :facultyId and pf.degreeEntity.id = :degreeId")
    Optional<PaidFacultyEntity> findByFacultyAndDegreeAndYear(@Param("year") Short year,
                                                              @Param("facultyId") Integer facultyId,
                                                              @Param("degreeId") Integer degreeId);

    @Query(value = "select s " +
            "from PaidFacultyEntity pf " +
            "join GroupEntity g on g.degreeEntity.id = pf.degreeEntity.id and g.facultyEntity.id = pf.facultyEntity.id " +
            "join StudentEntity s on s.groupEntity.id = g.id " +
            "where pf.year = :year " +
            "and pf.facultyEntity.id = :facultyId " +
            "and pf.degreeEntity.id = :degreeId " +
            "and year(g.startDate) = :year")
    List<StudentEntity> getAllPaidStudents(@Param("year") Short year,
                                           @Param("facultyId") Integer facultyId,
                                           @Param("degreeId") Integer degreeId);
}
