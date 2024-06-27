package com.labgenomics.practice.config.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final UserDetailsService userDetailsService;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request // http 요청에 대한 인가 설정 처리
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        // dispatcherTypeMatchers : View Resolver 등을 통해 페이지 이동을 하면 반드시 설정에 추가(안그러면 무수히 많은 redirect 요청)
                        // spring security 6.0부터 forward 방식 페이지 이동에도 default로 인증이 걸리도록 변경되었음
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/static/**"),
                                new AntPathRequestMatcher("/login*"),
                                new AntPathRequestMatcher("/registration"),
                                new AntPathRequestMatcher("/sign-up"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/api*"),
                                new AntPathRequestMatcher("/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui/**")
//                                new AntPathRequestMatcher("/login-process")

                        ).permitAll()
                        .anyRequest().authenticated()    // 어떠한 요청이라도 인증필요
                )
                .formLogin(login -> login    // form 방식 로그인 사용
                        .loginPage("/login")    // 우리가 만든 로그인 페이지
                        .loginProcessingUrl("/login-process")    // submit 받을 url, loginProcessingUrl 에 입력한 url로 submit을 하면 Security가 가로채 내부적으로 처리된다.
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureHandler(customAuthenticationFailureHandler())
                )


        ;

        return http.build();
    }


    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        customAuthenticationSuccessHandler.setDefaultUrl("/");
        customAuthenticationSuccessHandler.setTarget("/home");
        return customAuthenticationSuccessHandler;
    }

    // 로그인 인증 실패 후 handler
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        customAuthenticationFailureHandler.setRedirect("fail");
        customAuthenticationFailureHandler.setMessage("message");
        customAuthenticationFailureHandler.setDefaultFailureUrl("/login");
        return customAuthenticationFailureHandler;
    }




}