package com.demo.user_service.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.user_service.data.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecurityUser implements UserDetails {

	private Long id;
	private String email;
	private String name;
	private String userId;

	public SecurityUser(Authentication authentication) {}

	public SecurityUser(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.name = user.getName();
		this.userId = user.getUserId();
	}

	public SecurityUser(Long id, String email, String name, String userId) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.userId = userId;
	}

	public SecurityUser(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("AUTHORITY"));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}
}
