package com.demo.order_service.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.demo.order_service.dto.OrderDto;

public interface OrderService {
	void createOrder(OrderDto orderDto, String userId);
	List<OrderDto> findAllOrderDtoByUserId(String userId);
	OrderDto findOrderDtoByOrderId(String orderId);
}
