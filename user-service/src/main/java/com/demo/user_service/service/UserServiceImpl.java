package com.demo.user_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.user_service.auth.JwtTokenProvider;
import com.demo.user_service.data.dto.LoginDto;
import com.demo.user_service.data.dto.TokenDto;
import com.demo.user_service.data.dto.UserDto;
import com.demo.user_service.data.entity.User;
import com.demo.user_service.global.exception.category.NotFoundException;
import com.demo.user_service.global.exception.data.ErrorCode;
import com.demo.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void createUser(UserDto userDto) {
		userDto.createUserId();
		userDto.encryptPwd(passwordEncoder);
		userRepository.save(userDto.toEntity());
	}

	@Override
	public List<UserDto> findAllUser() {
		return userRepository.findAllUserDto();
	}

	@Override
	public UserDto findUser(long userId) {
		UserDto userDto = userRepository.findUserDtoById(userId).orElseThrow(() -> new NotFoundException("UserServiceImpl.findUser", ErrorCode.UNDEFINED_USER));
		// userDto.setOrderDtos();
		return userDto;
	}

	@Override
	public TokenDto login(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
			.orElseThrow(() -> new NotFoundException("", ErrorCode.UNDEFINED_USER));

		if (!user.passwordMatches(passwordEncoder, loginDto.getPassword())) {
			throw new IllegalArgumentException();
		}

		String accessToken = createAccessToken(user.getId());
		return TokenDto.builder().accessToken(accessToken).email(user.getEmail()).build();
	}

	private String createAccessToken(long userId) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(userId, Collections.singleton(new SimpleGrantedAuthority("AUTHORITY")));
		Map<String, String> tokenMap = jwtTokenProvider.generateToken(userId, authentication);
		return tokenMap.get("access");
	}
}
