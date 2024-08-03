package com.demo.user_service.global.exception.data;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// USER
	UNDEFINED_USER(404, "회원을 찾을 수 없습니다.");

	private final int status;
	private final String message;

	ErrorCode(final int status, final String message) {
		this.status = status;
		this.message = message;
	}
}
