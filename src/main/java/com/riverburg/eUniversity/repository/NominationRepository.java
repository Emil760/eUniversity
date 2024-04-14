package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.NominationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NominationRepository extends JpaRepository<NominationEntity, Integer> {

    Optional<NominationEntity> findByName(String name);

    @Query(value = "select n.id from NominationEntity n where n.isActive = true")
    List<Integer> getActiveNominationIds();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(n.id, n.name) " +
            "from NominationEntity n " +
            "where n.isActive = true")
    List<DDLResponse<Integer>> getNominationsDDL();
}
