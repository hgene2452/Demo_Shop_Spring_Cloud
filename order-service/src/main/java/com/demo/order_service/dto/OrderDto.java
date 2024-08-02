package com.demo.order_service.dto;

import com.demo.order_service.entity.Order;

import lombok.Data;

@Data
public class OrderDto {
	private String productId;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
	private String orderId;
	private String userId;

	public Order toEntity() {
		return Order.builder()
			.productId(this.productId)
			.qty(this.qty)
			.unitPrice(this.unitPrice)
			.totalPrice(this.totalPrice)
			.userId(this.userId)
			.orderId(this.orderId)
			.build();
	}

	public OrderDto(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String orderId,
		String userId) {
		this.productId = productId;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.orderId = orderId;
		this.userId = userId;
	}
}
