package com.demo.user_service.global.exception.category;

import com.demo.user_service.global.exception.data.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessRuntimeException extends RuntimeException {
	private final String messageKey;
	private final ErrorCode errorCode;
	private final Object[] params;

	public BusinessRuntimeException(String messageKey, ErrorCode errorCode, Object... params) {
		this.messageKey = messageKey;
		this.errorCode = errorCode;
		this.params = params;
	}
}
