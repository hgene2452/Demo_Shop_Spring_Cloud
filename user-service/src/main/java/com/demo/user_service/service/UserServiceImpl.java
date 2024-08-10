package com.demo.user_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.user_service.auth.JwtTokenProvider;
import com.demo.user_service.clients.OrderServiceClient;
import com.demo.user_service.data.dto.LoginDto;
import com.demo.user_service.data.dto.OrderDto;
import com.demo.user_service.data.dto.TokenDto;
import com.demo.user_service.data.dto.UserDto;
import com.demo.user_service.data.entity.User;
import com.demo.user_service.global.exception.category.NotFoundException;
import com.demo.user_service.global.exception.category.UnAuthorizedException;
import com.demo.user_service.global.exception.data.ErrorCode;
import com.demo.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final OrderServiceClient orderServiceClient;
	private final Environment env;
	private final RestTemplate restTemplate;

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
	public UserDto findUser(String userId) {
		UserDto userDto = userRepository.findUserDtoByUserId(userId).orElseThrow(() -> new NotFoundException("UserServiceImpl.findUser", ErrorCode.UNDEFINED_USER));

		/*
			[restTemplate 방식을 통해 order-service로부터 데이터 받아오기]

			String orderUrl = String.format(env.getProperty("order_service.url"), userId);
			ResponseEntity<List<OrderDto>> orderDtoResp = restTemplate.exchange(
				orderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {}
			);
			List<OrderDto> orderDtoList = orderDtoResp.getBody();
		 */

		/* openFeign 방식을 통해 order-service로부터 데이터 받아오기 */
		List<OrderDto> orderDtoList = orderServiceClient.getOrders(userId);
		userDto.setOrderDtos(orderDtoList);

		return userDto;
	}

	@Override
	public TokenDto login(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.getEmail())
			.orElseThrow(() -> new NotFoundException("", ErrorCode.UNDEFINED_USER));

		if (!user.passwordMatches(passwordEncoder, loginDto.getPassword())) {
			throw new UnAuthorizedException("UserServiceImpl.login", ErrorCode.FORBIDDEN_USER);
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
