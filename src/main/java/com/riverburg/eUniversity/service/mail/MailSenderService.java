package com.riverburg.eUniversity.service.mail;

public interface MailSenderService {

    void sendPasswordResetMail(String mail);

    void sendRegistrationCredentialsMail(String mail, String login, String password);
}
