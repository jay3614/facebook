package com.facebook.dto;

import java.time.LocalDateTime;

import com.facebook.entity.PostEntity;
import com.facebook.entity.ReplyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	
	private Long rno;
	private String replyer;
	private String content;
	private Long id;
	private LocalDateTime regDate;
	
	
	public ReplyDTO(final ReplyEntity entity) {
		this.rno = entity.getRno();
		this.replyer = entity.getReplyer();
		this.content = entity.getContent();
		this.id = entity.getPost().getId();
		this.regDate = entity.getRegDate();
	}
	
	public static ReplyEntity dtoToEntity(final ReplyDTO dto) {
		
		PostEntity pEntity = PostEntity.builder().id(dto.getId()).build();
		
		ReplyEntity entity = ReplyEntity.builder()
			.replyer(dto.getReplyer()).content(dto.getContent())
			.post(pEntity).build();
		
		return entity;
	}
	
}
