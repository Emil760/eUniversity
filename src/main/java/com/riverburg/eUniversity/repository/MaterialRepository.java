package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.DisciplineEntity;
import com.riverburg.eUniversity.model.entity.MaterialEntity;
import com.riverburg.eUniversity.model.entity.SectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {

    @Query(value = "select m " +
            "from MaterialEntity m " +
            "where m.sectorEntity.id = :sectorId AND m.disciplineEntity.id = :disciplineId " +
            "AND (m.fileEntity.originalFileName like %:search% or m.description like %:search%)")
    Page<MaterialEntity> findAllByDisciplineAndSector(Pageable pageable,
                                                      @Param("disciplineId") Integer disciplineId,
                                                      @Param("sectorId") Integer sectorId,
                                                      @Param("search") String search);

}
