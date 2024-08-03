package com.demo.user_service.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenDto {
	private String email;
	private String accessToken;

	@Builder
	public TokenDto(String email, String accessToken) {
		this.email = email;
		this.accessToken = accessToken;
	}
}
