package com.facebook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebook.entity.ReplyEntity;
import com.facebook.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyService {
	
	private final ReplyRepository replyRepository;
	
	// 댓글 작성
	public List<ReplyEntity> create(final ReplyEntity rEntity){
		
		replyRepository.save(rEntity);
		
		List<ReplyEntity> result = replyRepository.findAll();
		
		return result;
	}
	
	// 댓글 리스트 리턴
	public List<ReplyEntity> retrieve(List<Long> id){
		
		List<ReplyEntity> result = replyRepository.listByPostId(id);
		
		return result;
	}
	
	// rno에 해당하는 댓글 삭제
	public void deleteReply(Long rno){
		
		try {
			replyRepository.deleteById(rno);
		} catch (Exception e) {
			log.error("삭제 시 예외 발생");
			throw new RuntimeException("삭제 시 예외 발생" + rno);
		}
	}
	
	// postId에 해당하는 댓글 전체 삭제
	public void deleteAll(Long postId) {
		
		replyRepository.deleteAllByPostId(postId);
		
	}
	
}
