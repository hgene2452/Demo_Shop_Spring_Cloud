package com.demo.order_service.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.order_service.dto.OrderDto;
import com.demo.order_service.service.KafkaProducer;
import com.demo.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

	private final OrderService orderService;
	private final KafkaProducer kafkaProducer;

	@PostMapping("/{userId}/orders")
	public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto, @PathVariable("userId") String userId) {
		orderService.createOrder(orderDto, userId);

		// kafka를 통해 주문내역 전달
		kafkaProducer.send("example-catalog-topic", orderDto);

		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<OrderDto>> findAllOrderByUserId(@PathVariable("userId") String userId) {
		List<OrderDto> orderDtos = orderService.findAllOrderDtoByUserId(userId);
		return ResponseEntity.ok(orderDtos);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderDto> findOrderByOrderId(@PathVariable String orderId) {
		OrderDto orderDto = orderService.findOrderDtoByOrderId(orderId);
		return ResponseEntity.ok(orderDto);
	}
}
