package com.facebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.facebook.entity.UserEntity;
import com.facebook.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	// 사용자 생성
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null) {
			throw new RuntimeException("사용자 정보가 없음");
		}
		
		// 사용자 이메일로부터 사용자 존재여부 확인
		final String email = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("이미 존재하는 이메일 입니다.");
			throw new RuntimeException("이미 존재하는 이메일 입니다.");
		}
		
		return userRepository.save(userEntity);
	}
	
	// 이메일&비밀번호로 사용자 검증
	public UserEntity getByCredential(final String email, final String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	public UserEntity getByCredential(final String email, final String password, final PasswordEncoder encoder) {
		
		final UserEntity originalUser = userRepository.findByEmail(email);
		
		// matches() 메서드로 비밀번호가 같은지 여부 확인
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
	
	// 이메일 존재 검증
	public boolean checkEmail(String memberEmail) {
		return userRepository.existsByEmail(memberEmail);
	}
	
	// 임시 패스워드 생성
	public String getTmpPassword() {
		char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        log.info("임시 비밀번호 생성 : " + pwd);

        return pwd;
	}
	
	// 임시 패스워드로 업데이트
	public void updatePassword(String tmpPassword, String memberEmail) {
		String encryptPassword = passwordEncoder.encode(tmpPassword);
        if(userRepository.findByEmail(memberEmail) != null) {
        	UserEntity user = userRepository.findByEmail(memberEmail);
        	user.updatePassword(encryptPassword);
        	log.info("임시 비밀번호 업데이트 : " + tmpPassword);
        } else {
        	throw new IllegalArgumentException("해당 사용자가 없습니다."); 
        }

	}
	
	// id에 해당하는 사용자의 userName 리턴
	public String getUserName(String id) {
		
		String result = userRepository.findNameById(id);
		
		return result;
	}
	
	// userId 리스트에 해당하는 사용자 객체 리스트 리턴
	public List<UserEntity> getUserInfo(List<String> userIds) {
	    List<UserEntity> result = userRepository.findAllByUserIdIn(userIds);
	    return result;
	}
	
	// id에 해당하는 사용자 객체 리턴
	public UserEntity findById(String id) {
	    Optional<UserEntity> result = userRepository.findById(id);
	    return result.orElse(null);
	}
	
}
