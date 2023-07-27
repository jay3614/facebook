package com.facebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})	// email에 유니크 제약 조건 추가
public class UserEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;	// 사용자에게 고유하게 부여되는 id
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String birthday;
	
	@Column(nullable = false)
	private String gender;
	
	/** 임시 패스워드로 설정하기 위한 변경 메서드 **/
	public void updatePassword(String password) {
		this.password = password;
	}
	
}
