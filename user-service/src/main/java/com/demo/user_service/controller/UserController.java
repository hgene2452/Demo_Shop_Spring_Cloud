package com.demo.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.user_service.dto.UserDto;
import com.demo.user_service.entity.User;
import com.demo.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final Environment env;

	@Value("${greeting.message}")
	private String greetingMessage;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}

	@GetMapping("/welcome")
	public String welcome() {
		return greetingMessage;
	}

	@PostMapping("/users")
	public String createUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		return "Create user method is called";
	}

	@GetMapping("/users")
	public List<User> findAllUser() {
		return userService.findAllUser();
	}

	@GetMapping("/users/{userId}")
	public User findUser(@PathVariable("userId") long userId) {
		return userService.findUser(userId);
	}
}
