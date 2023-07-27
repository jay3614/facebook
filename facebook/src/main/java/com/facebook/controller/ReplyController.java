package com.facebook.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.dto.ReplyDTO;
import com.facebook.dto.ResponseDTO;
import com.facebook.entity.ReplyEntity;
import com.facebook.service.ReplyService;
import com.facebook.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("reply")
public class ReplyController {
	
	private final ReplyService replyService;
	private final UserService userService;
	
	// 댓글 작성
	@PostMapping("/{postId}")
	public ResponseEntity<?> addReply(@AuthenticationPrincipal String id, @RequestBody ReplyDTO dto, @PathVariable("postId") Long postId){
		String userName = userService.getUserName(id);
		
		dto.setReplyer(userName);
		
		dto.setId(postId);
		
		ReplyEntity entity = ReplyDTO.dtoToEntity(dto);
		
		List<ReplyEntity> list = replyService.create(entity);
		
		List<ReplyDTO> dtos = list.stream().map(ReplyDTO::new).collect(Collectors.toList());
		
		ResponseDTO<ReplyDTO> response = ResponseDTO.<ReplyDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	// 댓글 삭제
	@DeleteMapping("/{rno}")
	public ResponseEntity<?> deleteReplyByPostId(@AuthenticationPrincipal String id, @PathVariable("rno") Long rno){
		
		replyService.deleteReply(rno);
		
		ResponseDTO<ReplyDTO> response = ResponseDTO.<ReplyDTO>builder().data4(rno).build();
		return ResponseEntity.ok().body(response);
	}
	
}
