package com.demo.user_service.auth;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	@Getter
	private final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 30분
	@Getter
	private final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 7일
	private final Key key;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	public JwtTokenProvider(@Value("${jwt.secret}") String key) {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Map<String, String> generateToken(Long id, Authentication authentication) {
		SecurityUser securityUser = new SecurityUser(id, String.valueOf(authentication.getPrincipal()));
		String authorities = securityUser.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		String accessToken = createToken(now, id, ACCESS_TOKEN_EXPIRE_TIME, Jwts.builder()
			.claim("created", now)
			.claim("id", id)
			.claim("expiresIn", ACCESS_TOKEN_EXPIRE_TIME)
			.claim("auth", authorities));
		String refreshToken = createToken(now, id, REFRESH_TOKEN_EXPIRE_TIME, Jwts.builder());

		HashMap<String, String> map = new HashMap<>();
		map.put("access", accessToken);
		map.put("refresh", refreshToken);

		return map;
	}

	private String createToken(long now, Long id, long EXPIRE_TIME, JwtBuilder authentication) {
		Date tokenExpiresIn = new Date(now + EXPIRE_TIME);
		return authentication
			.setExpiration(tokenExpiresIn)
			.claim("id", id)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		if (claims.get("auth") == null) {
			throw new IllegalArgumentException();
		}

		String id = String.valueOf(claims.get("id"));
		SecurityUser securityUser = (SecurityUser)userDetailsService.loadUserByUsername(id);
		List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();

		return new UsernamePasswordAuthenticationToken(securityUser, "", authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
			throw e;
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			throw e;
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			throw e;
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException exception) {
			return exception.getClaims();
		}
	}
}
