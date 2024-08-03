package com.demo.user_service.global.exception.data;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final int status;
	private final String message;

	/**
	 * 커스텀 에러 반환타입
	 *
	 * @param errorCode
	 * @param errorMessage
	 */
	public ErrorResponse(HttpStatus errorCode, String errorMessage) {
		this.status = errorCode.value();
		this.message = errorMessage;
	}

	/**
	 * 서버 에러 반환타입
	 *
	 * @param errorCode
	 */
	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
	}
}
