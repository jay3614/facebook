package com.facebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.facebook.dto.PostDTO;
import com.facebook.entity.PostEntity;
import com.facebook.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	
	// 글 작성
	public List<PostEntity> create(final PostEntity entity){
		validate(entity);
		
		postRepository.save(entity);
		
		log.info("저장된 Entity ID : " + entity.getId());
		
		List<PostEntity> result = postRepository.findByUserId(entity.getUserId());
		
		return result;
	}
	
	// 사용자 검증
	private void validate(final PostEntity entity) {
		
		if(entity == null) {
			log.warn("Entity가 null임");
			throw new RuntimeException("Entity가 null임");
		}
		
		if(entity.getUserId() == null) {
			log.warn("없는 사용자입니다.");
			throw new RuntimeException("없는 사용자입니다.");
		}
	}
	
	// 글목록 조회
	public List<PostEntity> retrieve(){
		
//		List<PostEntity> result = postRepository.findAll();
		List<PostEntity> result = postRepository.findByOrderByRegDateDesc();
		
		return result;
	}
	
	// 글 수정
	public List<PostEntity> update(final PostEntity entity) {
		
		validate(entity);
		
		final Optional<PostEntity> original = postRepository.findById(entity.getId());
		
		original.ifPresent(post->{
			post.setContent(entity.getContent());
			
			postRepository.save(post);
		});
		
		List<PostEntity> result = retrieve();
		
		return result;
	}
	
	// 글 삭제
	public List<PostEntity> delete(final PostEntity entity){
		
		validate(entity);
		
		try {
			postRepository.delete(entity);
		} catch (Exception e) {

			log.error("삭제 시 예외 발생");
			throw new RuntimeException("삭제 시 예외 발생" + entity.getId());
		}
		return retrieve();
	}
	
	public PostDTO detail(final Long id){
		
		PostEntity entity = postRepository.getById(id);
		
		PostDTO result = new PostDTO(entity);
		
		return result;
	}
	
}
