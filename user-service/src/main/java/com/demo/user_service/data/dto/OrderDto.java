package com.demo.user_service.data.dto;

import lombok.Data;

@Data
public class OrderDto {
	private String productId;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
	private String orderId;
	private String userId;

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
