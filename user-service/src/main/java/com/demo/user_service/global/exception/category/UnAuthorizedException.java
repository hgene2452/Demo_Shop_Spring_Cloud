package com.demo.user_service.global.exception.category;

import com.demo.user_service.global.exception.data.ErrorCode;

public class UnAuthorizedException extends BusinessRuntimeException {
	protected static final String MESSAGE_KEY = "error.UnAuthorized";

	public UnAuthorizedException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
