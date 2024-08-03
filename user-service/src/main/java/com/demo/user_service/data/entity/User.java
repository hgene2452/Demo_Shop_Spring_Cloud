package com.demo.user_service.data.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, unique = true)
	private String userId;

	@Column(nullable = false, unique = true)
	private String encryptedPwd;

	@Builder
	public User(String email, String name, String userId, String encryptedPwd) {
		this.email = email;
		this.name = name;
		this.userId = userId;
		this.encryptedPwd = encryptedPwd;
	}

	public boolean passwordMatches(PasswordEncoder passwordEncoder, String rawPassword) {
		return passwordEncoder.matches(rawPassword, encryptedPwd);
	}
}
