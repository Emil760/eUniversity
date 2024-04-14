package com.riverburg.eUniversity.service.security;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.mapper.DataMapper;
import com.riverburg.eUniversity.model.constant.Role;
import com.riverburg.eUniversity.model.security.ResetPasswordRequest;
import com.riverburg.eUniversity.model.dto.request.post.AuthorizationRequest;
import com.riverburg.eUniversity.model.dto.request.put.ChangePasswordRequest;
import com.riverburg.eUniversity.model.dto.request.post.RegistrationRequest;
import com.riverburg.eUniversity.model.dto.response.indentity.AuthorizationResponse;
import com.riverburg.eUniversity.model.dto.response.indentity.TokensResponse;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.RefreshTokenEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.repository.user.RefreshTokenRepository;
import com.riverburg.eUniversity.service.account.AccountService;
import com.riverburg.eUniversity.service.mail.AccountMailService;
import com.riverburg.eUniversity.service.mail.MailSenderService;
import com.riverburg.eUniversity.util.token.JwtTokenUtil;
import com.riverburg.eUniversity.util.token.RefreshTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountAuthServiceImpl implements AccountAuthService {

    private final DataMapper mapper;

    private final AccountMailService accountMailService;

    private final JwtTokenUtil jwtTokenUtil;

    private final RefreshTokenUtil refreshTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final AccountService accountService;

    private final MailSenderService mailSenderService;

    @Override
    public AccountEntity register(RegistrationRequest request, Role role) throws RestException {
        validateRegister(request);

        AccountEntity accountEntity = mapper.registrationRequestToAccountEntity(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        accountEntity.setUpdatedAt(new Date());
        accountEntity.setPassword(encodedPassword);
        accountEntity.setRole(role);
        accountEntity.setIsActive(false);
        accountEntity.setMail(request.getMail());

        accountService.save(accountEntity);

        try {
            mailSenderService.sendRegistrationCredentialsMail(request.getMail(),
                    request.getLogin(), request.getPassword());
        } catch (Exception ex) {
            System.err.println("Mail doesn't send " + ex.getMessage());
        }

        return accountEntity;
    }

    @Override
    public AuthorizationResponse authorize(AuthorizationRequest request) throws RestException {
        AccountEntity account = accountService.findByLogin(request.getLogin());

        if (!account.getIsActive())
            throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());

        UserDetails userDetails;
        try {
            var authenticate = authenticationManager.authenticate(authentication);

            userDetails = (UserDetails) authenticate.getPrincipal();
        } catch (AuthenticationException authenticationException) {
            throw RestException.of(ErrorConstant.LOGIN_FAILED);
        }

        String jwt = jwtTokenUtil.generateToken(userDetails);
        String rt = refreshTokenUtil.generateRefreshToken(account);

        return new AuthorizationResponse(account.getId(), account.getFullName(), account.getRole(), jwt, rt);
    }

    @Override
    @Transactional
    public TokensResponse refreshTokens(String refreshToken) throws RestException {
        RefreshTokenEntity rt = refreshTokenUtil.validateRefreshToken(refreshToken);

        AccountEntity account = accountService.findByLogin(rt.getAccountEntity().getLogin());

        if (!account.getIsActive())
            throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

        rt.setIsExpired(true);
        refreshTokenRepository.save(rt);

        String newJwt = jwtTokenUtil.generateToken(account.getLogin());
        String newRt = refreshTokenUtil.generateRefreshToken(account);

        return new TokensResponse(newJwt, newRt);
    }

    @Override
    public void changeStatus(UUID accountId, boolean status) throws RestException {
        AccountEntity account = accountService.findById(accountId);

        account.setIsActive(status);
        account.setUpdatedAt(new Date());

        accountService.save(account);
    }

    @Override
    @Transactional
    public void changePassword(AccountAuthenticationContext accountAuthenticationContext, ChangePasswordRequest request) throws RestException {
        if (request.getOldPassword().equals(request.getNewPassword()))
            throw RestException.of(ErrorConstant.DUPLICATE_PASSWORD);

        Authentication authentication = new
                UsernamePasswordAuthenticationToken(accountAuthenticationContext.getUsername(), request.getOldPassword());

        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException badCredentialsException) {
            throw RestException.of(ErrorConstant.LOGIN_FAILED);
        }

        AccountEntity account = accountService.findByLogin(accountAuthenticationContext.getUsername());

        if (!account.getIsActive())
            throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        account.setUpdatedAt(new Date());

        List<RefreshTokenEntity> rtList = refreshTokenRepository
                .findAllByAccountEntity(account);

        rtList.forEach(rt -> rt.setIsExpired(true));

        accountService.save(account);
        refreshTokenRepository.saveAll(rtList);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        var account = accountMailService.findAccountByResetToken(request.getMailToken());

        if (!account.getIsActive())
            throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

        refreshTokenRepository.updateToExpiredTokensByAccountEntity(account);

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        boolean isChanged = accountService.updateAccountPassword(encodedPassword, account.getLogin(), new Date()) > 0;

        if (!isChanged)
            throw RestException.of(ErrorConstant.PASSWORD_NOT_CHANGED);

        accountMailService.acceptResetMailToken(request.getMailToken());
    }

    @Override
    public void revokeRefreshToken(AccountAuthenticationContext accountAuthenticationContext, String refreshToken) {

        RefreshTokenEntity rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> RestException.of(ErrorConstant.REFRESH_TOKEN_NOT_FOUND));

        if (rt.getAccountEntity().getLogin().equals(accountAuthenticationContext.getUsername()))
            rt.setIsExpired(true);
        else
            throw RestException.of(ErrorConstant.REFRESH_TOKEN_NOT_FOUND);
    }

    //TODO add regex format for login and password
    private void validateRegister(RegistrationRequest request) throws RestException {

        if (request.getLogin() == null || request.getLogin().isBlank())
            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, "Login is empty");

        try {
            accountService.findByLogin(request.getLogin());
            throw RestException.of(ErrorConstant.UNIQUE_VALIDATION, "Username already exists");
        }
        catch (Exception ignore) { }

        if (request.getFullName() == null || request.getFullName().isBlank())
            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, "Full name is empty");

        if (request.getPassword() == null || request.getPassword().isBlank())
            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, "Password is empty");

        if (request.getAge() <= 0)
            throw RestException.of(ErrorConstant.INVALID_PARAMETERS, "Age can`t be equal or less than zero");
    }

}
