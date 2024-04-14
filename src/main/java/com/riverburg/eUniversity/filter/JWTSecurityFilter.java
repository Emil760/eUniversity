package com.riverburg.eUniversity.filter;

import com.riverburg.eUniversity.exception.RestException;
import com.riverburg.eUniversity.exception.constant.ErrorConstant;
import com.riverburg.eUniversity.exception.handler.FilterExceptionHandler;
import com.riverburg.eUniversity.model.entity.AccountEntity;
import com.riverburg.eUniversity.model.security.AccountAuthenticationContext;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.util.token.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JWTSecurityFilter extends OncePerRequestFilter {

    private final FilterExceptionHandler filterExceptionHandler;

    private final JwtTokenUtil jwtTokenUtil;

    private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);

                String username = jwtTokenUtil.getLoginFromToken(jwt);

                if (jwtTokenUtil.isTokenExpired(jwt))
                    throw RestException.of(ErrorConstant.ACCESS_TOKEN_EXPIRED);

                AccountEntity account = accountRepository.findByLogin(username)
                        .orElseThrow(() -> RestException.of(ErrorConstant.LOGIN_FAILED));

                if (!account.getIsActive())
                    throw RestException.of(ErrorConstant.ACCOUNT_IS_DEACTIVATED);

                if (jwtTokenUtil.getIssuedAtFromToken(jwt).before(account.getUpdatedAt()))
                    throw RestException.of(ErrorConstant.ACCESS_TOKEN_EXPIRED);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        AccountAuthenticationContext
                                .builder()
                                .accountId(account.getId())
                                .username(account.getLogin())
                                .build(),
                        null, List.of(new SimpleGrantedAuthority(account.getRole().name())));

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw RestException.of(ErrorConstant.ACCESS_TOKEN_REQUIRED);
            }

        } catch (RestException ex) {
            filterExceptionHandler.handleException(ex, response);
            return;
        } catch (JwtException ex) {
            filterExceptionHandler.handleException(RestException
                    .of(ErrorConstant.ACCESS_TOKEN_INVALIDATED), response);
            return;
        } catch (Exception ex) {
            filterExceptionHandler.handleException(RestException
                    .of(ErrorConstant.SERVER_ERROR), response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}