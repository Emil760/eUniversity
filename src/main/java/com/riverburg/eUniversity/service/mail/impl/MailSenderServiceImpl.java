package com.riverburg.eUniversity.service.mail.impl;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.service.mail.AccountMailService;
import com.riverburg.eUniversity.service.mail.MailSenderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${storage.mail.reset-password}")
    private String resetMailHtmlPath;

    @Value("${storage.mail.registration}")
    private String registrationMailHtmlPath;

    @Value("${url.mail.registration}")
    private String registrationUrl;

    @Value("${url.mail.reset-password}")
    private String resetPasswordUrl;

    @NonNull
    private final JavaMailSender mailSender;

    @NonNull
    private final AccountMailService accountMailService;

    @Override
    public void sendPasswordResetMail(String mail) {
        var account = accountMailService.findByMail(mail);

        String generatedResetToken = RandomString.make(25);

        boolean isCreated = accountMailService.createResetTokenMail(generatedResetToken, account.getId());

        if (!isCreated)
            throw RestException.of(ErrorConstant.SERVER_ERROR);

        try {
            var messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);

            messageHelper.setFrom("noreply@gmail.com");
            messageHelper.setTo(account.getMail());
            messageHelper.setSubject("Reset Password");

            String htmlText = this.getHtmlText(resetMailHtmlPath);

            htmlText = htmlText
                    .replace("reset-password-link",
                            String.format("%s/%s", this.resetPasswordUrl, generatedResetToken));

            messageHelper.setText(htmlText, true);

            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception ex) {
            throw RestException.of(ErrorConstant.SERVER_ERROR);
        }
    }

    @Override
    public void sendRegistrationCredentialsMail(String mail, String login, String password) {
        try {
            var messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);

            messageHelper.setFrom("noreply@gmail.com");
            messageHelper.setTo(mail);
            messageHelper.setSubject("Welcome");

            String htmlText = this.getHtmlText(registrationMailHtmlPath);

            htmlText = htmlText
                    .replace("login-page", this.registrationUrl)
                    .replace("login-value", login)
                    .replace("password-value", password);

            messageHelper.setText(htmlText, true);
            System.err.println("Send mail...");
            mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception ex) {
            throw RestException.of(ErrorConstant.SERVER_ERROR);
        }
    }

    private String getHtmlText(String relativePath) throws IOException {
        Path fullPath = Paths.get(relativePath).toAbsolutePath().normalize();

        return Files.readString(fullPath);
    }
}
