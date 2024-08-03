package com.demo.user_service.service;

import java.util.List;

import com.demo.user_service.data.dto.LoginDto;
import com.demo.user_service.data.dto.TokenDto;
import com.demo.user_service.data.dto.UserDto;

public interface UserService {
	void createUser(UserDto userDto);
	List<UserDto> findAllUser();
	UserDto findUser(long userId);
	TokenDto login(LoginDto loginDto);
}
