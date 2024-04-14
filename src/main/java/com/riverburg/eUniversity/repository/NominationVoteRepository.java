package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.dto.response.nomination.INominationVotesResponse;
import com.riverburg.eUniversity.model.entity.NominationVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NominationVoteRepository extends JpaRepository<NominationVoteEntity, Integer> {

    @Query(value = "select " +
            "case when exists(select * from nomination_votes nv " +
            "                 where nv.account_id = :accountId " +
            "                 and nv.nomination_id = :nominationId) " +
            "then cast(1 as bit) " +
            "else cast(0 as bit) end;", nativeQuery = true)
    boolean isAlreadyNominated(@Param("accountId") String accountId,
                               @Param("nominationId") int nominationId);

    @Query(value = "select nv.teacher_id as accountId, nv.nomination_id as nominationId, count(account_id) as votes " +
            "from nomination_votes nv " +
            "group by teacher_id, nomination_id", nativeQuery = true)
    List<INominationVotesResponse> getAllVotes();
}
