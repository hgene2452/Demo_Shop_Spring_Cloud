package com.demo.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.demo.order_service.dto.OrderDto;
import com.demo.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public void createOrder(OrderDto orderDto, String userId) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		orderDto.setUserId(userId);
		orderRepository.save(orderDto.toEntity());
	}

	@Override
	public List<OrderDto> findAllOrderDtoByUserId(String userId) {
		return orderRepository.findAllOrderDtoByUserId(userId);
	}

	@Override
	public OrderDto findOrderDtoByOrderId(String orderId) {
		return orderRepository.findOrderDtoByOrderId(orderId).orElseThrow(IllegalAccessError::new);
	}
}
