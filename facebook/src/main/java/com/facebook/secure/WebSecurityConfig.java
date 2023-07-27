package com.facebook.secure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 수행할 인증 필터를 주입 받기
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and()
		.csrf().disable()
		.httpBasic().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)	// 세션 기반이 아닌 인증으로 변경
		.and().authorizeRequests().antMatchers("/", "/auth/**", "/reply/**").permitAll()
		.anyRequest().authenticated();	// 나머지 패스에 대해선 인증 적용
		
		// 필터 등록 작업. 특정 필터 이후에 수행하라고 지정할 수 있음
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		
	}
	
	
}
