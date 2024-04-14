package com.riverburg.eUniversity.service.account;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.dto.response.base.DDLResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void activateAccount(UUID id, boolean isActive) throws RestException {
        var account = findById(id);

        account.setIsActive(isActive);

        accountRepository.save(account);
    }

    @Override
    public List<DDLResponse<UUID>> getAccountsDDL(Role role) {
        return accountRepository.getAccountsDDL(role);
    }

    @Override
    public int updateAccountPassword(String password, String login, Date date) {
        return accountRepository.updateAccountPassword(password, login, date);
    }

    @Override
    public void save(AccountEntity entity) {
        this.accountRepository.save(entity);
    }

    @Override
    public AccountEntity findById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ACCOUNT_NOT_FOUND);
                });
    }

    @Override
    public AccountEntity findByLogin(String login) {
        return accountRepository.findByLogin(login)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.ACCOUNT_NOT_FOUND);
                });
    }

}
