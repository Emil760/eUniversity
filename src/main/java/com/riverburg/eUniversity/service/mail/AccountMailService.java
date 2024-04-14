package com.riverburg.eUniversity.service.mail;

import com.riverburg.eUniversity.model.entity.AccountEntity;

import java.util.UUID;

public interface AccountMailService {

    AccountEntity findByMail(String mail);

    AccountEntity findAccountByResetToken(String resetToken);

    boolean createResetTokenMail(String resetToken, UUID accountId);

    boolean acceptResetMailToken(String resetToken);
}
