package com.facebook.dto;

import java.time.LocalDateTime;

import com.facebook.entity.HeartEntity;
import com.facebook.entity.PostEntity;
import com.facebook.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeartDTO {
	
	private Long id;
	private String user;
	private Long postId;
	
	public HeartDTO(final HeartEntity entity) {
		this.id = entity.getId();
		this.user = entity.getUser().getId();
		this.postId = entity.getPost().getId();
	}
	
	public static HeartEntity dtoToEntity(final HeartDTO dto) {
		
		PostEntity pEntity = PostEntity.builder().id(dto.getPostId()).build();
		UserEntity uEntity = UserEntity.builder().id(dto.getUser()).build();
		
		HeartEntity entity = HeartEntity.builder()
			.user(uEntity).post(pEntity)
			.build();
		
		return entity;
	}
	
}
