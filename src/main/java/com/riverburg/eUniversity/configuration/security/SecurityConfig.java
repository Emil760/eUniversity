package com.riverburg.eUniversity.configuration.security;

import com.riverburg.eUniversity.exception.handler.FilterExceptionHandler;
import com.riverburg.eUniversity.filter.JWTSecurityFilter;
import com.riverburg.eUniversity.repository.user.AccountRepository;
import com.riverburg.eUniversity.service.security.AccountDetailsService;
import com.riverburg.eUniversity.util.token.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationContext applicationContext;
    private final AccountDetailsService accountDetailsService;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/webjars/**",
            "/h2-console/**",
            "/actuator/**",
            "/main/**",
            "/authentication",
            "/authentication/reset-password/**",
            "/authentication/refresh-tokens/**"
    };

    // add filter for authorize requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin-panel/**").hasAuthority("ADMIN")
                .antMatchers("/student-panel/**").hasAuthority("STUDENT")
                .antMatchers("/teacher-panel/**").hasAuthority("TEACHER")
                .antMatchers("/avatar/add-by-account-id", "/avatar/remove-by-account-id").hasAuthority("ADMIN")
                .anyRequest().authenticated().and()         
                .addFilterBefore(new JWTSecurityFilter(
                                applicationContext.getBean(FilterExceptionHandler.class),
                                applicationContext.getBean(JwtTokenUtil.class),
                                applicationContext.getBean(AccountRepository.class)),
                        UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
    }

    // ignore filter for specified paths
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.accountDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
