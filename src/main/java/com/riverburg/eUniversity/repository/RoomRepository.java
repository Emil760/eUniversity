package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    @Query(value = "select r " +
            "from RoomEntity r " +
            "where (r.number like %:search% or r.eduProcessEntity.name like %:search%) " +
            "and (:active = -1 " +
            "     or r.isActive = cast(:active as boolean))")
    Page<RoomEntity> findByName(Pageable pageable,
                                @Param("search") String search,
                                @Param("active") int active);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(r.id, r.number) " +
            "from RoomEntity r " +
            "where r.isActive = true")
    List<DDLResponse<Integer>> getRoomsDDL();

    Optional<RoomEntity> findByNumber(String number);

    @Query(value = "select r " +
            "from RoomEntity r " +
            "where r.id <> :id and r.number = :number")
    Optional<RoomEntity> findByNumberExceptCurr(@Param("id") Integer id,
                                                @Param("number") String number);

    @Query(value = "select case when exists (" +
            " select * from rooms where edu_process_id = :eduProcessId" +
            " )" +
            " then cast(1 as bit)" +
            " else cast(0 as bit)" +
            " end;", nativeQuery = true)
    Boolean isEduProcessPresent(@Param("eduProcessId") Integer eduProcessId);
}
