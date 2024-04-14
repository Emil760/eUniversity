package com.riverburg.eUniversity.service.account;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountEntity findById(UUID id);

    AccountEntity findByLogin(String login);

    void save(AccountEntity entity);

    void activateAccount(UUID id, boolean isActive) throws RestException;

    List<DDLResponse<UUID>> getAccountsDDL(Role role);

    int updateAccountPassword(String password, String login, Date date);
}
