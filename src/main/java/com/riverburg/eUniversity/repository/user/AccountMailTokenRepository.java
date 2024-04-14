package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.entity.AccountMailTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountMailTokenRepository extends JpaRepository<AccountMailTokenEntity, Integer> {

    Optional<AccountMailTokenEntity> findByResetToken(String resetToken);

    @Modifying
    @Query(value = "INSERT INTO account_mail_tokens(reset_token, account_id, created_at, is_accepted) " +
            "VALUES(:token, :accountId, :createdAt, 0)", nativeQuery = true)
    int createResetTokenMail(@Param("token") String token,
                             @Param("accountId") String accountId,
                             @Param("createdAt") Date date);

    @Modifying
    @Query(value = "UPDATE AccountMailTokenEntity a " +
            "SET a.isAccepted = true " +
            "WHERE a.resetToken = :token")
    int revokeResetTokenMail(@Param("token") String token);
}
