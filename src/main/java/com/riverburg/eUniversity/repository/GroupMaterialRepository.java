package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.GroupMaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMaterialRepository extends JpaRepository<GroupMaterialEntity, Integer> {

    @Query(value = "select gm " +
            "from GroupMaterialEntity gm " +
            "where gm.groupEntity.id = :groupId " +
            "and gm.facultyDisciplineEntity.id = :facultyDisciplineId " +
            "and (:eduProcessId = 0 or gm.eduProcessEntity.id = :eduProcessId) " +
            "and gm.fileEntity.fileName like %:search%")
    Page<GroupMaterialEntity> findMaterialsOfGroupByDisciplineAndEduProcess(Pageable pageable,
                                                                              @Param("groupId") Integer groupId,
                                                                              @Param("facultyDisciplineId") Integer facultyDisciplineId,
                                                                              @Param("eduProcessId") Integer eduProcessId,
                                                                              @Param("search") String search);
}
