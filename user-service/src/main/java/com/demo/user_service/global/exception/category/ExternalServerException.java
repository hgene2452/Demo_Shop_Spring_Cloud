package com.demo.user_service.global.exception.category;

import com.demo.user_service.global.exception.data.ErrorCode;

public class ExternalServerException extends BusinessRuntimeException {

	protected static final String MESSAGE_KEY = "error.ExternalServer";

	public ExternalServerException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
