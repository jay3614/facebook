package com.facebook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.dto.ResponseDTO;
import com.facebook.dto.UserDTO;
import com.facebook.entity.UserEntity;
import com.facebook.secure.TokenProvider;
import com.facebook.service.MailService;
import com.facebook.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Log4j2
public class UserController {

	private final UserService userService;
	private final MailService mailService;

	// 추가적으로 토큰을 생성(provide) 및 전달하는 beans 추가
	private final TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

		try{
			// 입력한 정보를 dto에 담은 후 entity로 변환
			UserEntity user = UserEntity.builder().email(userDTO.getEmail()).userName(userDTO.getUserName())
					.password(passwordEncoder.encode(userDTO.getPassword())).birthday(userDTO.getBirthday())
					.gender(userDTO.getGender()).build();

			UserEntity registerUser = userService.create(user);

			UserDTO responseDTO = UserDTO.builder().email(registerUser.getEmail()).userName(registerUser.getUserName())
					.id(registerUser.getId()).password(registerUser.getPassword()).birthday(registerUser.getBirthday())
					.gender(registerUser.getGender()).build();

			return ResponseEntity.ok().body(responseDTO);
		}catch(Exception e) {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	// 로그인
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {

		// dto에 담긴 입력값들을 비밀번호는 암호화하여 엔티티로 변환
		UserEntity user = userService.getByCredential(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

		if(user != null) {	// 존재하는 유저라면 토큰 부여
			final String token = tokenProvider.create(user);

			final UserDTO responseDTO = UserDTO.builder().userName(user.getUserName()).email(user.getEmail())
					.id(user.getId()).token(token).build();
			return ResponseEntity.ok().body(responseDTO);

		}else {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error("로그인 실패").build();

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	// 비밀번호 찾기(임의의 새 비밀번호를 발급하여 이메일로 전송)
	@PostMapping("/reset")
	public ResponseEntity<?> sendPwdEmail(@RequestBody UserDTO userDTO) throws Exception {
		log.info("요청된 이메일 : " + userDTO.getEmail());

		if(!userService.checkEmail(userDTO.getEmail())) {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error("해당 이메일을 사용하는 회원을 찾을 수 없습니다. " + userDTO.getEmail())
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}

		String tmpPassword = userService.getTmpPassword();	// 랜덤의 비밀번호 생성
		userService.updatePassword(tmpPassword, userDTO.getEmail());
		mailService.createMail(tmpPassword, userDTO.getEmail());	// 임시 비밀번호 메일로 전송
		final UserDTO responseDTO = UserDTO.builder().email(userDTO.getEmail()).build();
		return ResponseEntity.ok().body(responseDTO);
	}

	// 로그인 한 회원 정보를 표시
	@GetMapping
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal String id){
		
		UserEntity userInfo = userService.findById(id);
		
		UserDTO dto = new UserDTO();
		dto.setUserName(userInfo.getUserName());
		dto.setBirthday(userInfo.getBirthday());
		dto.setGender(userInfo.getGender());
		dto.setEmail(userInfo.getEmail());
		
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data5(dto).build();
		
		return ResponseEntity.ok().body(response);
	}

}
