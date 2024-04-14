package com.riverburg.eUniversity.repository.user;

import com.riverburg.eUniversity.model.entity.AdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    @Query(value = "select a " +
            "from AdminEntity a " +
            "where (a.accountEntity.fullName like %:search% " +
            "       or a.accountEntity.login like %:search% " +
            "       or a.accountEntity.mail like %:search%) " +
            "and (:active = -1 " +
            "     or a.accountEntity.isActive = cast(:active as boolean))")
    Page<AdminEntity> findAdmins(Pageable pageable, String search, Integer active);
}
