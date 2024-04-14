package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByLogin(String login);

    Optional<AccountEntity> findByMail(String mail);

    Optional<AccountEntity> findByIdAndIsActive(UUID id, boolean isActive);

    @Query(value = "SELECT a.avatarFileEntity FROM AccountEntity a WHERE a.id = :id")
    Optional<FileEntity> findAvatarById(@Param("id") UUID id);

    @Query(value = "SELECT a.descriptionFileEntity FROM AccountEntity a WHERE a.id = :id")
    Optional<FileEntity> findDescriptionById(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE AccountEntity a SET a.avatarFileEntity.id = :fileId WHERE a.id = :id")
    void updateAvatarByAccountIdAndFileId(@Param("id") UUID id, @Param("fileId") Long fileId);

    @Modifying
    @Query(value = "UPDATE AccountEntity a SET a.descriptionFileEntity.id = :fileId WHERE a.id = :id")
    void updateDescriptionByAccountIdAndFileId(@Param("id") UUID id, @Param("fileId") Long fileId);

    @Modifying
    @Query(value = "UPDATE AccountEntity a SET a.avatarFileEntity.id = null WHERE a.id = :id")
    void removeAvatarByAccountId(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE AccountEntity a SET a.descriptionFileEntity.id = null WHERE a.id = :id")
    void removeDescriptionByAccountId(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE AccountEntity a " +
            "SET a.password = :password, a.updatedAt = :date " +
            "WHERE a.login = :login")
    int updateAccountPassword(@Param("password") String password,
                              @Param("login") String login,
                              @Param("date") Date date);

    @Query(value = "select new com.riverburg.eUniversity.model.dto.response.base.DDLResponse(a.id, a.fullName) " +
            "from AccountEntity a " +
            "where a.isActive = true " +
            "and a.role = :role")
    List<DDLResponse<UUID>> getAccountsDDL(@Param("role") Role role);
}