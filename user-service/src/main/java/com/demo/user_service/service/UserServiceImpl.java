package com.demo.user_service.service;

import org.springframework.stereotype.Service;

import com.demo.user_service.dto.UserDto;
import com.demo.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public void createUser(UserDto userDto) {
		userDto.createUserId();
		userDto.encryptPwd();

		userRepository.save(userDto.toEntity());
	}
}
