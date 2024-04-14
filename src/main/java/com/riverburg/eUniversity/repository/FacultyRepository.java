package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.FacultyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity, Integer> {

    @Query(value = "select f " +
            "from FacultyEntity f " +
            "where (f.name like %:search% " +
            "      or f.shortName like %:search%) " +
            "and (:active = -1 " +
            "     or f.isActive = cast(:active as boolean))")
    Page<FacultyEntity> findByNameOrShortName(Pageable pageable,
                                              @Param("search") String search,
                                              @Param("active") int active);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(f.id, f.shortName)" +
            "from FacultyEntity f " +
            "where f.isActive = true")
    List<DDLResponse<Integer>> findAllIsActiveShortDDL();

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(f.id, f.name)" +
            "from FacultyEntity f " +
            "where f.isActive = true")
    List<DDLResponse<Integer>> findAllIsActiveDDL();

    List<FacultyEntity> findAllByIsActive(Boolean isActive);

    Optional<FacultyEntity> findByNameOrShortName(String name, String shortName);

    @Query(value = "select f " +
            "from FacultyEntity f " +
            "where f.id = :id and f.isActive = :isActive")
    Optional<FacultyEntity> findByIdAndActive(@Param("id") Integer id,
                                              @Param("isActive") Boolean isActive);

    @Query(value = "select f " +
            "from FacultyEntity f " +
            "where f.id <> :id and (f.name = :name or f.shortName = :shortName)")
    Optional<FacultyEntity> findByNameOrShortNameExceptCurr(@Param("id") Integer id,
                                                            @Param("name") String name,
                                                            @Param("shortName") String shortName);
}
