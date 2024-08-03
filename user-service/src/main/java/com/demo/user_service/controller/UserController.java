package com.demo.user_service.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.user_service.dto.UserDto;
import com.demo.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
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
	public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> findAllUser() {
		List<UserDto> userDtos = userService.findAllUser();
		return ResponseEntity.ok(userDtos);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> findUser(@PathVariable("userId") long userId) {
		UserDto userDto = userService.findUser(userId);
		return ResponseEntity.ok(userDto);
	}
}
