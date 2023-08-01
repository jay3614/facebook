package com.facebook.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ResponseDTO<T> {
	
	private String error;
	private List<T> data;
	private PostDTO data2;
	private List<UserDTO> data3;
	private Long data4;
	private UserDTO data5;
	private List<HeartDTO> data6;
}
