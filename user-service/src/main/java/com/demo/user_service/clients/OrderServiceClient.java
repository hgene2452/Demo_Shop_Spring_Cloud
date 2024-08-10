package com.demo.user_service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.user_service.data.dto.OrderDto;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

	@GetMapping("/order-service/{userId}/orders")
	List<OrderDto> getOrders(@PathVariable("userId") String userId);
}
