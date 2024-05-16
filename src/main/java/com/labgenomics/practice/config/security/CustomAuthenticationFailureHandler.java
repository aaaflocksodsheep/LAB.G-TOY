package com.labgenomics.practice.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Setter
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String id;
	private String password;
	private String redirect;
	private String message;
	private String defaultFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		
		log.info("failure {}", request);
		
		String username = request.getParameter(id);
		String pwd = request.getParameter(password);
		String loginRedirect = request.getParameter(redirect);
		
		request.setAttribute(id, username);
		request.setAttribute(password, pwd);
		request.setAttribute(redirect, loginRedirect);
		
		// Request 객체의 Attribute에 예외 메시지 저장
		request.setAttribute(message, exception.getMessage());
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
		
	}

}
