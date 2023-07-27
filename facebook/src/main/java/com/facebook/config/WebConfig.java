package com.facebook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final long MAX_AGE_SECS = 3600;	// 1시간

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:3000") // 허용할 오리진
		.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 HTTP 메서드
		.allowedHeaders("*") // 허용할 HTTP 헤더
		.allowCredentials(true) // 쿠키를 허용 여부
		.maxAge(MAX_AGE_SECS); // 캐시 시간 설정
	}
}
