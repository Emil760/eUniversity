package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.DegreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DegreeRepository extends JpaRepository<DegreeEntity, Integer> {
    
    Optional<DegreeEntity> findByName(String name);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(d.id, d.name)" +
            "from DegreeEntity d")
    List<DDLResponse<Integer>> findAllDDL();
}
