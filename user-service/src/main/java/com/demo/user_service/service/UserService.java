package com.demo.user_service.service;

import java.util.List;

import com.demo.user_service.dto.UserDto;

public interface UserService {
	void createUser(UserDto userDto);
	List<UserDto> findAllUser();
	UserDto findUser(long userId);
}
