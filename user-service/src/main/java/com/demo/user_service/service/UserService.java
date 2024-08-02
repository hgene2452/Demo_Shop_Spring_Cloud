package com.demo.user_service.service;

import java.util.List;

import com.demo.user_service.dto.UserDto;
import com.demo.user_service.entity.User;

public interface UserService {
	void createUser(UserDto userDto);
	List<User> findAllUser();
}
