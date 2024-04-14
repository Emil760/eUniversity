package com.riverburg.eUniversity.service.security;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountEntity account = accountRepository.findByLogin(username)
                .orElseThrow(() -> {
                    throw RestException.of(ErrorConstant.LOGIN_FAILED);
                });

        if (!account.getIsActive())
            throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole().name()));

        return User.builder()
                .username(account.getLogin())
                .password(account.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
