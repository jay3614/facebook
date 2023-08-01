package com.facebook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facebook.entity.HeartEntity;
import com.facebook.repository.HeartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartService {
	
	private final HeartRepository heartRepository;
	
	// 좋아요 클릭
	public List<HeartEntity> addHeart(final HeartEntity entity) {
		
		heartRepository.save(entity);
		
		List<HeartEntity> result = heartRepository.findAll();
		
		return result;
	}
	
	// 좋아요 갯수 리턴
	public int countHeart(final HeartEntity entity) {
		int result = heartRepository.getCountByPostId(null);
		
		return result;
	}
	
	
	// 좋아요 해제
	private void removeHeart() {
		
		
		
	}
	
}
