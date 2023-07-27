package com.facebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebook.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
	
	// 작성자 아이디로 글 리턴
	List<PostEntity> findByUserId(String userId);
	
	// 전체 글 리스트 내림차순으로 리턴
	List<PostEntity> findByOrderByRegDateDesc();
}
