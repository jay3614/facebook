package com.facebook.dto;

import com.facebook.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 서버에서 발행한 토큰 정보를 담는 필드 하나를 더 추가 한다.
 * 필요에 따라서 DB에 저장할 수도 있는데, 이 값이 바뀔 경우를 생각해서 일단 필드로만 추가하여 요청 시마다 이 필드값과 비교하도록 한다.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	
	private String id;
	private String userName;
	private String email;
	private String password;
	private String token;
	private String birthday;
	private String gender;
	
	public UserDTO(final UserEntity entity) {
		this.id = entity.getId();
		this.userName = entity.getUserName();
		this.email = entity.getEmail();
		this.password = entity.getPassword();
		this.birthday = entity.getBirthday();
		this.gender = entity.getGender();
	}
	
}
