package com.demo.user_service.global.exception.data;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// USER
	FORBIDDEN_USER(403, "권한이 없는 서비스입니다."),
	UNDEFINED_USER(404, "회원을 찾을 수 없습니다."),
	// FEIGN
	FEIGN_COMMUNICATION(400, "feign 통신 중 오류가 발생하였습니다.");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
