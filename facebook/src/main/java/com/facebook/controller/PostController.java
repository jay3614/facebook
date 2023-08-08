package com.facebook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.dto.HeartDTO;
import com.facebook.dto.PostDTO;
import com.facebook.dto.ReplyDTO;
import com.facebook.dto.ResponseDTO;
import com.facebook.dto.UserDTO;
import com.facebook.entity.HeartEntity;
import com.facebook.entity.PostEntity;
import com.facebook.entity.ReplyEntity;
import com.facebook.entity.UserEntity;
import com.facebook.service.HeartService;
import com.facebook.service.PostService;
import com.facebook.service.ReplyService;
import com.facebook.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {
	
	private final UserService userService;
	private final PostService postService;
	private final ReplyService replyService;
	private final HeartService heartService;
	
	// 글 작성
	@PostMapping
	public ResponseEntity<?> createPost(@AuthenticationPrincipal String userId , @RequestBody PostDTO dto){
		
		try {
			PostEntity entity = PostDTO.dtoToEntity(dto);	// dto에 담긴 값을 엔티티로 변환
			
			entity.setId(null);
			
			entity.setUserId(userId);
			
			List<PostEntity> list = postService.create(entity);
			
			List<PostDTO> dtos = list.stream().map(PostDTO::new).collect(Collectors.toList());
			
			ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			String error = e.getMessage();
			
			ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	// 글 리스트 전체 조회
	@GetMapping
	public ResponseEntity<?> retrievePostList(@AuthenticationPrincipal String id){
		
		// post에 대한 responseDTO
		List<PostEntity> entities = postService.retrieve();
		List<PostDTO> dtos = entities.stream().map(PostDTO::new).collect(Collectors.toList());
		ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(dtos).build();
		
		// user에 대한 responseDTO
		List<String> userIds = dtos.stream().map(PostDTO::getUserId).collect(Collectors.toList());
		List<UserEntity> entities2 = userService.getUserInfo(userIds);
		List<UserDTO> dtos2 = entities2.stream().map(UserDTO::new).collect(Collectors.toList());
		ResponseDTO<UserDTO> response2 = ResponseDTO.<UserDTO>builder().data(dtos2).build();
		
		// reply에 대한 responseDTO
		List<Long> ids = dtos.stream().map(PostDTO::getId).collect(Collectors.toList());
		List<ReplyEntity> rEntity = replyService.retrieve(ids);
		List<ReplyDTO> dto = rEntity.stream().map(ReplyDTO::new).collect(Collectors.toList());
		ResponseDTO<ReplyDTO> response3 = ResponseDTO.<ReplyDTO>builder().data(dto).build();
		
		Map<String, Object> map = new HashMap<>();
		map.put("response1", response);
		map.put("response2", response2);
		map.put("response3", response3);
		
		return ResponseEntity.ok().body(map);
	}
	
	// 글 수정
	@PutMapping
	public ResponseEntity<?> update(@AuthenticationPrincipal String userId, @RequestBody PostDTO dto) {
		
		PostEntity entity = PostDTO.dtoToEntity(dto);
		
		entity.setUserId(userId);
		
		List<PostEntity> entities = postService.update(entity);
		
		List<PostDTO> dtos = entities.stream().map(PostDTO::new).collect(Collectors.toList());
		
		ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
	// 글 삭제
	@DeleteMapping
	public ResponseEntity<?> delete(@AuthenticationPrincipal String userId, @RequestBody PostDTO dto) {
		
		try {
			PostEntity entity = PostDTO.dtoToEntity(dto);
			
			entity.setUserId(userId);
			
			replyService.deleteAll(dto.getId());	// 글 삭제 전에 글의 id를 fk로 잡혀있는 댓글부터 삭제
			
			List<PostEntity> entities = postService.delete(entity);
			
			List<PostDTO> dtos = entities.stream().map(PostDTO::new).collect(Collectors.toList());
			
			ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			String error = e.getMessage();
			
			ResponseDTO<PostDTO> response = ResponseDTO.<PostDTO>builder().error(error).build();
			
			return ResponseEntity.badRequest().body(response);
		}
	}	// 좋아요 기록도 없애는 기능 추가해야함
	
	// 좋아요 기능
	@PostMapping("/{postId}")
	public ResponseEntity<?> addHeart(@AuthenticationPrincipal String userId , @RequestBody HeartDTO dto, @PathVariable("postId") Long postId){
		
		dto.setUser(userId);
		
		dto.setPostId(postId);
		
		// db에서 userId와 postId를 기준으로 검색하여 이미 좋아요를 눌렀는지 여부 체크 후 안눌렀다면 if문 실행, 눌렀다면 else문 실행하여 좋아요와 취소를 구분
		if(heartService.existHeart(userId, postId) == 0) {
			HeartEntity entity = HeartDTO.dtoToEntity(dto);
			
			List<HeartEntity> list = heartService.addHeart(entity);
			
			List<HeartDTO> dtos = list.stream().map(HeartDTO::new).collect(Collectors.toList());
			
			ResponseDTO<ReplyDTO> response = ResponseDTO.<ReplyDTO>builder().data6(dtos).build();
			
			return ResponseEntity.ok().body(response);
		}else if(heartService.existHeart(userId, postId) == 1){
			
			heartService.removeHeart(userId, postId);
			
			return ResponseEntity.ok().body(null);
		}else { // 에러가 발생하면 넘어오는 구간
			
			System.out.println("에러 발생");
			
			return ResponseEntity.ok().body(null);
		}
		
	}
	
}
