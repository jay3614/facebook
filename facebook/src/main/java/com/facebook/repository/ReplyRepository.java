package com.facebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facebook.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

	// id 리스트에 포함된 post_id를 가지고 있는 댓글 객체 리턴 
	@Query(value = "SELECT * from Reply where post_id IN :id", nativeQuery = true)
	List<ReplyEntity> listByPostId(@Param("id") List<Long> id);
	
	// postId에 해당하는 댓글 삭제
	@Query(value = "DELETE from Reply where post_id =:postId", nativeQuery = true)
	void deleteAllByPostId(@Param("postId") Long postId);
	
}
