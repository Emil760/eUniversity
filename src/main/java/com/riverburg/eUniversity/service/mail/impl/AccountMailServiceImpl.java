package com.riverburg.eUniversity.service.mail.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.AccountMailTokenEntity;
import com.riverburg.eUniversity.repository.user.AccountMailTokenRepository;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.service.mail.AccountMailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountMailServiceImpl implements AccountMailService {

    private final AccountRepository accountRepository;

    private final AccountMailTokenRepository accountMailTokenRepository;

    @Override
    public AccountEntity findByMail(String mail) {
        return accountRepository.findByMail(mail)
                .orElseThrow(() -> RestException.of(ErrorConstant.MAIL_NOT_FOUND));
    }

    @Override
    public AccountEntity findAccountByResetToken(String resetToken) {
        return accountMailTokenRepository
                .findByResetToken(resetToken)
                .map(AccountMailTokenEntity::getAccountEntity)
                .orElseThrow(() -> RestException.of(ErrorConstant.RESET_TOKEN_MAIL_NOT_FOUND));

    }

    @Override
    @Transactional
    public boolean createResetTokenMail(String resetToken, UUID accountId) {
        return accountMailTokenRepository.createResetTokenMail(resetToken, accountId.toString(), new Date()) > 0;
    }

    @Override
    @Transactional
    public boolean acceptResetMailToken(String resetToken) {
        return accountMailTokenRepository.revokeResetTokenMail(resetToken) > 0;
    }
}
