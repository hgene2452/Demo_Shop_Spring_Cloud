package com.demo.user_service.dto;

import java.util.Date;
import java.util.UUID;

import com.demo.user_service.entity.User;

import lombok.Data;

@Data
public class UserDto {
	private String email;
	private String name;
	private String pwd;
	private String userId;
	private Date createdAt;
	private String encryptedPwd;

	public void createUserId() {
		this.userId = UUID.randomUUID().toString();
	}

	public void encryptPwd() {
		this.encryptedPwd = this.pwd;
	}

	public User toEntity() {
		return User.builder()
			.email(this.email)
			.name(this.name)
			.userId(this.userId)
			.encryptedPwd(this.encryptedPwd)
			.build();
	}
}
