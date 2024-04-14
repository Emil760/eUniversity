package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByToken(String token);

    List<RefreshTokenEntity> findAllByAccountEntity(AccountEntity account);

    @Modifying
    @Query(value = "UPDATE RefreshTokenEntity r " +
            "SET r.isExpired = true " +
            "WHERE r.accountEntity = :account")
    void updateToExpiredTokensByAccountEntity(@Param("account") AccountEntity account);
}
