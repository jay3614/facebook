package com.facebook.secure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenProvider tokenProvider;
	
	// Http 요청의 헤더를 파싱해서 그 중 token에 해당하는 정보를 파싱해서 Bearer 토큰을 리턴하도록 하는 메서드 정의
	private String parseBearerToken(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");	// 헤더 정보를 가져오기
		
		// bearerToken 또는 bearer라고 명시되었는지 값 조회
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			// 요청에서 토큰 가져오기
			String token = parseBearerToken(request);
			log.info("Token 인증 필터 Running.......");
			
			// 받아낸 토큰을 검사(null or empty)
			if(token != null && !token.equalsIgnoreCase("null")) {
				// 토큰에서 userId(UUID) 가져오기
				String userId = tokenProvider.validateAndGetUserId(token);
				log.info("인증 사용자 ID : " + userId);
				
				// 인증이 완료된 사용자의 경우 securityContext에 등록
				AbstractAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				
				securityContext.setAuthentication(authenticationToken);
				
				SecurityContextHolder.setContext(securityContext);
			}
		} catch (Exception e) {
			logger.error("사용자 인증 작업을 수행하지 못했음");
		}
		filterChain.doFilter(request, response);
	}
}
