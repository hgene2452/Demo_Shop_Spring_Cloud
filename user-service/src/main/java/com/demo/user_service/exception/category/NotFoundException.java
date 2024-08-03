package com.demo.user_service.exception.category;

import com.demo.user_service.exception.data.ErrorCode;

public class NotFoundException extends BusinessRuntimeException {

	protected static final String MESSAGE_KEY = "error.NotFound";

	public NotFoundException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
