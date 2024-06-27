package com.labgenomics.practice.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // session 여부 확인
        HttpSession session = request.getSession(false);

        if (
                // js, img, css 등 허용
                request.getRequestURI().startsWith("/static")
                // 외부 dependencies 허용
                || request.getRequestURI().startsWith("/webjars")
                // 내부 api 사용시, api 허용; 단, api 경로는 반드시 '/api' 또는 '/apitest/로 시작
                // swagger 포함
                || request.getRequestURI().startsWith("/api")
                // default, login, error 경로 허용
                || request.getRequestURI().startsWith("/")
                || request.getRequestURI().startsWith("/login")
                || request.getRequestURI().startsWith("/error")
        ) {
            return true;
        }

        if (session == null) {
            return false;
        }

        if (!isAjax(request)) {
            return false;
        }

        return true;
    }


    public boolean isAjax(HttpServletRequest request) {
   		Optional<Object> test = Optional.ofNullable(request.getHeader("AJAX"));
   		if(test.isPresent()) {
   			return true;
   		} else {
   			return false;
   		}
   	}


}
