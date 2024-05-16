package com.labgenomics.practice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration {



    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;



    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        customAuthenticationSuccessHandler.setDefaultUrl("/");
        return customAuthenticationSuccessHandler;
    }

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        customAuthenticationFailureHandler.setId("username");
        customAuthenticationFailureHandler.setPassword("password");
        customAuthenticationFailureHandler.setRedirect("fail");
        customAuthenticationFailureHandler.setMessage("message");
        customAuthenticationFailureHandler.setDefaultFailureUrl("/login");
        return customAuthenticationFailureHandler;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers(
                                new AntPathRequestMatcher("/webjars/**"),
                                new AntPathRequestMatcher("/redirect"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/static/**")
                        ).permitAll()
                )
//                .formLogin(formLogin ->
//                        formLogin.loginPage("/login")
//                                .loginProcessingUrl("/login-process")
//                                .defaultSuccessUrl("/")
//                                .successHandler(customAuthenticationSuccessHandler())
//                                .failureHandler(customAuthenticationFailureHandler())
//                )
                .httpBasic(withDefaults());
        return http.build();
    }


}