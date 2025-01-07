package com.sbs.qna_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 메서드 보안 활성화
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /*.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/question/list")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/question/detail/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/signup")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/style.css")).permitAll()
                        .anyRequest().authenticated() // 그 외의 요청은 인증이 필요
                ) // 해당 경로는 인증없이 접근 가능*/

                .formLogin(formLogin -> formLogin
//                        .usernameParameter("name") // 아이디 필드 이름 변경
//                        .passwordParameter("pw") // 비밀번호 필드 이름 변경

                        // GET
                        // 시큐리티에게 우리가 만든 로그인 페이지의 url을 알려줌
                        // 만약에 하지 않으면 기본 로그인 페이지의 url은 /login
                        .loginPage("/user/login") // 로그인 폼으로 이동

                        // POST
                        // 시큐리티에게 로그인 폼 처리 url을 알려줌
                        .loginProcessingUrl("/user/login") // 로그인 처리 시 요청 경로
                        .defaultSuccessUrl("/")) // 로그인 성공 시 리다이렉트 경로

                .logout(logout -> logout
                        // GET
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        // POST
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)) // 세션 무효화
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // 스프링 시큐리티에서 인증을 처리
    // 커스텀 인증 로직을 구현할 때 필요
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}