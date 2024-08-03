package com.demo.user_service.auth;

import org.apache.commons.lang.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {
	private static String AUTH_PARAM_NAME = "Authorization";
	private static String TOKEN_PREFIX = "Bearer";

	public static String getAuthParamName() {
		return AUTH_PARAM_NAME;
	}

	public static String getTokenPrefix() {
		return TOKEN_PREFIX;
	}

	public static String getAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTH_PARAM_NAME);
		if (StringUtils.isNotEmpty(bearerToken) && StringUtils.isNotBlank(bearerToken)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
