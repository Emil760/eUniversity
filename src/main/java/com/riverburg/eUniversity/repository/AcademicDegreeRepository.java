package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.AcademicDegreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AcademicDegreeRepository extends JpaRepository<AcademicDegreeEntity, Integer> {

    Optional<AcademicDegreeEntity> findByName(String name);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(ad.id, ad.name)" +
            "from AcademicDegreeEntity ad")
    List<DDLResponse<Integer>> findAllDDL();
}
