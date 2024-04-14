package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Integer> {

    Optional<SectorEntity> findByName(String name);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(s.id, s.name)" +
            "from SectorEntity s")
    List<DDLResponse<Integer>> findAllDDL();
}
