package com.demo.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.demo.gateway_service.provider.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	Environment env;
	JwtTokenProvider jwtTokenProvider;

	public AuthorizationHeaderFilter(Environment env, JwtTokenProvider jwtTokenProvider) {
		super(Config.class);
		this.env = env;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public static class Config {
		// Put configuration properties here
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// Header에 Authotization 정보가 없는 경우
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer ", "");

			// JWT 토큰이 유효하지 않은 경우
			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		});
	}

	// Mono, Flux -> Spring WebFlux
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		log.error(err);
		return response.setComplete();
	}

	// Header.Jwt 토큰의 subject(user key)와 유저의 요청에 담긴 유저키 값 비교 등의 인증 로직 추가 가능
	private boolean isJwtValid(String jwt) {
		boolean returnValue = true;
		Claims subject = jwtTokenProvider.parseClaims(jwt);
		if (subject == null || subject.isEmpty()) {
			returnValue = false;
		}
		return returnValue;
	}
}
