package com.facebook.dto;

import java.time.LocalDateTime;

import com.facebook.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
	
	private Long id;
	private String content;
	private String userId;
	private String keyword;
	private LocalDateTime regDate;
	
	public PostDTO(final PostEntity entity) {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.userId = entity.getUserId();
		this.regDate = entity.getRegDate();
	}
	
	public static PostEntity dtoToEntity(final PostDTO dto) {
		
		PostEntity entity = PostEntity.builder()
			.id(dto.getId()).content(dto.getContent())
			.userId(dto.getUserId())
			.build();
		
		return entity;
	}
	
}
