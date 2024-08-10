package com.demo.user_service.global.exception.decoder;

import com.demo.user_service.global.exception.category.FeignCommunicationException;
import com.demo.user_service.global.exception.category.NotFoundException;
import com.demo.user_service.global.exception.data.ErrorCode;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
			case 400:
				break;
			case 404:
				if (methodKey.contains("getOrders")) {
					return new NotFoundException("UserServiceImpl.findUser", ErrorCode.FEIGN_COMMUNICATION);
				}
				break;
			default:
				return new FeignCommunicationException("NotTracked: " + response.reason(), ErrorCode.FEIGN_COMMUNICATION);
		}

		return null;
	}
}
