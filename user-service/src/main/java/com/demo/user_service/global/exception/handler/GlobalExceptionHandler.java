package com.demo.user_service.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.user_service.global.exception.category.BusinessRuntimeException;
import com.demo.user_service.global.exception.category.ExternalServerException;
import com.demo.user_service.global.exception.category.NotFoundException;
import com.demo.user_service.global.exception.data.ErrorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	/**
	 * 404 에러
	 * 리소스 없음 에러 핸들러
	 *
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse notFoundCustomHandler(BusinessRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(exception.getErrorCode());
	}

	/**
	 * 500 에러
	 * 외부 서버와의 통신 에러 핸들러
	 *
	 * @param exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ExternalServerException.class)
	public ErrorResponse externalServerHandler(BusinessRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(exception.getErrorCode());
	}
}
