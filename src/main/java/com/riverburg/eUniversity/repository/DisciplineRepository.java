package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.DisciplineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<DisciplineEntity, Integer> {

    @Query(value = "select d " +
            "from DisciplineEntity d " +
            "where (d.name like %:search% " +
            "       or d.shortName like %:search%)" +
            "and (:active = -1 " +
            "     or d.isActive = cast(:active as boolean))")
    Page<DisciplineEntity> findByNameOrShortNameContaining(Pageable pageable,
                                                           @Param("search") String search,
                                                           @Param("active") Integer active);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(d.id, d.name)" +
            "from DisciplineEntity d " +
            "where d.isActive = true")
    List<DDLResponse<Integer>> findAllIsActiveDDL();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(d.id, d.shortName)" +
            "from DisciplineEntity d " +
            "where d.isActive = true")
    List<DDLResponse<Integer>> findAllIsActiveShortDDL();

    List<DisciplineEntity> findAllByIsActive(Boolean isActive);

    Optional<DisciplineEntity> findByNameOrShortName(String name, String shortName);

    @Query("select d " +
            "from DisciplineEntity d " +
            "where d.id = :id and d.isActive = :isActive")
    Optional<DisciplineEntity> findByIdAndActive(@Param("id") Integer id,
                                                 @Param("isActive") Boolean isActive);

    @Query(value = "select d " +
            "from DisciplineEntity d " +
            "where d.id <> :id and (d.name = :name or d.shortName = :shortName)")
    Optional<DisciplineEntity> findByNameOrShortNameExceptCurr(@Param("id") Integer id,
                                                               @Param("name") String name,
                                                               @Param("shortName") String shortName);
}
