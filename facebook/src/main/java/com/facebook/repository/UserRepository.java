package com.facebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facebook.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	// 이메일로 유저 객체 리턴
	UserEntity findByEmail(String email);
	
	// 이메일값 존재여부 확인
	Boolean existsByEmail(String email);
	
	// 이메일과 비밀번호로 유저 객체 리턴
	UserEntity findByEmailAndPassword(String email, String password);
	
	// id값에 해당하는 유저의 이름 리턴
	@Query(value = "SELECT user_name from User where id=:id", nativeQuery = true)
	String findNameById(@Param("id") String id);
	
	// userid 리스트에 포함된 id를 가지고 있는 유저 객체 리턴
	@Query(value = "SELECT * from User where id IN :userIds", nativeQuery = true)
	List<UserEntity> findAllByUserIdIn(@Param("userIds") List<String> userIds);
	
}
