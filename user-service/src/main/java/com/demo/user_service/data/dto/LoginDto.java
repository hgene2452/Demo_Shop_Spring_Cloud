package com.demo.user_service.data.dto;

import lombok.Data;

@Data
public class LoginDto {
	private String email;
	private String password;
}
