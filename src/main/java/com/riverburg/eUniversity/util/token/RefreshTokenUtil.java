package com.riverburg.eUniversity.util.token;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.entity.RefreshTokenEntity;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.repository.user.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenUtil {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    public static final long REFRESH_TOKEN_VALIDITY = 24 * 3600;

    @Transactional
    public String generateRefreshToken(AccountEntity account) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        LocalDateTime now = LocalDateTime.now();

        refreshToken.setAccountEntity(account);
        refreshToken.setIsExpired(false);
        refreshToken.setCreatedAt(now);
        refreshToken.setExpiredAt(now.plus(REFRESH_TOKEN_VALIDITY * 1000, ChronoUnit.MILLIS));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public RefreshTokenEntity validateRefreshToken(String token) throws RestException {
        LocalDateTime now = LocalDateTime.now();

        RefreshTokenEntity rt = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.REFRESH_TOKEN_NOT_FOUND);
                });

        if (rt.getIsExpired() || rt.getExpiredAt().compareTo(now) < 0)
            throw RestException.of(ErrorConstant.REFRESH_TOKEN_EXPIRED);

        return rt;
    }

    public boolean revokeRefreshToken(String token) throws RestException {
        Optional<RefreshTokenEntity> refreshToken = refreshTokenRepository.findByToken(token);

        if (!refreshToken.isPresent())
            throw RestException.of(ErrorConstant.REFRESH_TOKEN_NOT_FOUND);

        refreshToken.get().setIsExpired(true);

        refreshTokenRepository.save(refreshToken.get());
        return true;
    }
}

