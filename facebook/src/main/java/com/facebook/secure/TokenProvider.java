package com.facebook.secure;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.facebook.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenProvider {
	
	// 토큰 발행 시 기준이 되는 secret key 선언
	private static final String SECRET_KEY = "qkrtnqlswlrkrwoddlasdfafasefaefasfsfsefefsaf7asdf7safas8fsaeftgoagtrgagf7aserawv53a35ssdf";
	
	// 토큰 생성하는 메서드 정의
	public String create(UserEntity userEntity) {
		// 토큰의 유효기간(하루)을 설정하는 Date 객체 초기화
		Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
		
		// jwts의 builder()를 호출하여 header, payLoad를 설정하고 리턴
		String result = Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)	// 알고리즘 선택
				.setSubject(userEntity.getId())	//  토큰의 주체 설정(클라이언트 설정)
				.setIssuer("react_App")	// Token 발행자
				.setIssuedAt(new Date())	// 토큰 발행일자
				.setExpiration(expiryDate)	// 토크느 유효일
				.compact();
		
		return result;	// https://jwt.io/#libraries-io
	}
	/*
	 * parse()를 이용해서 BASE64로 디코딩 및 parsing을 수행한다.
	 * 헤더와 페이로드를 setSigninKey()로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교한다.
	 * 위조되지 않았다면 Claims(페이로드)를 리턴, 예외(위조시)인 경우 예외를 리턴한다.
	 * 위에서 조합을 userId로 했기 때문에 getBody를 호출하여 얻어내어 작업한다.
	 */
	 public String validateAndGetUserId(String token) {
		 
		 Claims claims = Jwts.parser()
				 .setSigningKey(SECRET_KEY)	// 복호화할 시크릿 키 적용
				 .parseClaimsJws(token)	// 파싱
				 .getBody();	// 조합 시 사용된 SECRET_KEY와 userId 중 userId를 리턴하는 메서드 호출
		 
		 return claims.getSubject();
	 }
}
