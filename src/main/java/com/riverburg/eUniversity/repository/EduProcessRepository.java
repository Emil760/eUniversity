package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.EduProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EduProcessRepository extends JpaRepository<EduProcessEntity, Integer> {

    Optional<EduProcessEntity> findByName(String name);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(e.id, e.name)" +
            "from EduProcessEntity e")
    List<DDLResponse<Integer>> findAllDDL();
}
