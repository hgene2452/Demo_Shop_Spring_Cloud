package com.demo.user_service.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.user_service.dto.UserDto;
import com.demo.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void createUser(UserDto userDto) {
		userDto.createUserId();
		userDto.encryptPwd(passwordEncoder);
		userRepository.save(userDto.toEntity());
	}

	@Override
	public List<UserDto> findAllUser() {
		return userRepository.findAllUserDto();
	}

	@Override
	public UserDto findUser(long userId) {
		UserDto userDto = userRepository.findUserDtoById(userId).orElseThrow(IllegalAccessError::new);
		// userDto.setOrderDtos();
		return userDto;
	}
}
