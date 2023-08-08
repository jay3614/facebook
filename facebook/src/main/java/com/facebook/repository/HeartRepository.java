package com.facebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facebook.entity.HeartEntity;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
	
	// 좋아요 누른 글의 post_id를 기준으로 갯수를 리턴
	@Query(value = "SELECT COUNT(*) AS num_hearts FROM Heart WHERE post_id =:postId", nativeQuery = true)
	int getCountByPostId(@Param("postId") Long postId);
	
	// 좋아요 누른 유저의 user_id와 누른 글의 post_id를 기준으로 이미 누른 글인지 갯수를 리턴하여 확인
	@Query(value = "SELECT COUNT(*) AS num_hearts FROM Heart WHERE user_id =:userId AND post_id =:postId", nativeQuery = true)
	int getCountByUserIdAndPostId(@Param("userId") String userId, @Param("postId") Long postId);
	
	// user_id와 post_id를 기준으로 해당하는 데이터 삭제
	@Query(value = "Delete FROM Heart WHERE user_id =:userId AND post_id =:postId", nativeQuery = true)
	int deleteByUserIdAndPostId(@Param("userId") String userId, @Param("postId") Long postId);
}
