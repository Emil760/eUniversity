package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;
import com.riverburg.eUniversity.model.entity.NominationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface NominationHistoryRepository extends JpaRepository<NominationHistoryEntity, Integer> {

    @Query(value = "select bth " +
            "from NominationHistoryEntity bth " +
            "where bth.date = :date")
    List<NominationHistoryEntity> findAll(@Param("date") Date date);

    @Query(value = "select bth " +
            "from NominationHistoryEntity bth " +
            "where bth.accountEntity.id = :accountId")
    List<NominationHistoryEntity> findByAccountId(@Param("accountId") UUID accountId);

    @Query(value = "select distinct bth.date " +
            "from NominationHistoryEntity bth")
    List<Date> getDatesDDL();

    @Query(value = "select n.id as nominationId, n.[name] as nomination, a.full_name as fullName, count(a.full_name) as votes " +
            "from nomination_votes nv " +
            "inner join accounts a on a.id = nv.teacher_id " +
            "inner join nominations n on n.id = nv.nomination_id " +
            "group by n.[name], a.full_name", nativeQuery = true)
    List<INominationVotesResponse> aaa();
}
