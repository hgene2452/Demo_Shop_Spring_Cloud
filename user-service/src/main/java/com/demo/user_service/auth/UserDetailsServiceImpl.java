package com.demo.user_service.auth;

import static com.demo.user_service.global.exception.data.ErrorCode.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.user_service.data.entity.User;
import com.demo.user_service.global.exception.category.NotFoundException;
import com.demo.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new NotFoundException("UserDetailsServiceImpl.loadUserByUsername", UNDEFINED_USER));
		return new SecurityUser(user);
	}
}
