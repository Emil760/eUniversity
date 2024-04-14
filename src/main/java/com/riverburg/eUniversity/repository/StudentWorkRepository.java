package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.StudentEntity;
import com.riverburg.eUniversity.model.entity.StudentWorkEntity;
import com.riverburg.eUniversity.model.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentWorkRepository extends JpaRepository<StudentWorkEntity, Integer> {

    Optional<StudentWorkEntity> findByThemeEntityAndStudentEntity(ThemeEntity themeEntity, StudentEntity studentEntity);

    Optional<StudentWorkEntity> findByStudentEntityAndId(StudentEntity studentEntity, int studentWorkId);

    List<StudentWorkEntity> findAllByThemeEntityIsIn(List<ThemeEntity> themeEntities);

    List<StudentWorkEntity> findAllByThemeEntityIdAndStudentEntity(Integer themeEntityId, StudentEntity studentEntity);

    List<StudentWorkEntity> findAllByStudentEntityIdAndThemeEntityIsIn(Integer studentId, List<ThemeEntity> themeEntities);
}
