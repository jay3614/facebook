package com.facebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facebook.entity.HeartEntity;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
	
	// 좋아요 누른 글의 id를 기준으로 갯수를 리턴
	@Query(value = "SELECT *(count) from Heart where post_id =:postId", nativeQuery = true)
	int getCountByPostId(@Param("postId") Long postId);
	
}
