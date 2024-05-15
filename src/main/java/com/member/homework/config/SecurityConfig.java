package com.member.homework.config;

import com.member.homework.common.security.AuthenticationHeaderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationHeaderFilter authenticationHeaderFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .authorizeHttpRequests(authorizedHttpRequest ->
                        // 로그인 API는 전부 접근을 허용해주고, ADMIN의 경우 계정에 권한이 있는지 Service Layer에서 판단
                        authorizedHttpRequest.requestMatchers("/api/admin/login", "/api/user/login").permitAll()
                        // 관리자용 API는 ADMIN 권한 필요
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        // 일반 사용자용 API는 관리자와 회원의 권한 둘 중 하나만 있어도 접근이 가능하다.
                        .requestMatchers("/api/**").hasAnyAuthority("ADMIN", "MEMBER")
                ).addFilterBefore(authenticationHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
