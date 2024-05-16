package com.labgenomics.practice.config.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Objects;

@Component
@Getter
@Setter
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	

	private String target;
	private String defaultUrl;
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


	public CustomAuthenticationSuccessHandler() {
		this.target = "";
		this.defaultUrl = "/";
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {


		//세션이상하면 걍 박살냄
		sessionInvalidate(request);
		HttpSession session = request.getSession(true);


		SecurityContext con = SecurityContextHolder.getContext();

		Authentication auth = con.getAuthentication();
		log.info("Auth session {}", auth);
		log.info("Auth session {}", authentication.getAuthorities());



		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null) {
			requestCache.removeRequest(request, response);
			redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
		}

		log.info("로그인 성공 {}", authentication.getAuthorities());

		redirectStrategy.sendRedirect(request, response, getTarget());
	}
	
	void sessionInvalidate(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session == null)
			return;
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}


	private String getClientIpAddress(HttpServletRequest request) {
		// X-Forwarded-For 헤더를 확인하여 프록시를 통한 클라이언트 IP 추출
		String xForwardedForHeader = request.getHeader("X-Forwarded-For");
		if (xForwardedForHeader != null && xForwardedForHeader.length() > 0) {
			return xForwardedForHeader.split(",")[0].trim();
		}
		// 프록시를 통해 오지 않은 경우, 직접 IP 주소 얻기
		return Objects.equals(request.getRemoteAddr(), "0:0:0:0:0:0:0:1") ?"127.0.0.1" : request.getRemoteAddr();
	}

}
