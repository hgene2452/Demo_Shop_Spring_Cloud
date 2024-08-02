package com.demo.catalog_service.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CatalogDto implements Serializable {
	private String productId;
	private String productName;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
	private String orderId;
	private String userId;

	public CatalogDto(String productId, String productName, Integer qty, Integer unitPrice) {
		this.productId = productId;
		this.productName = productName;
		this.qty = qty;
		this.unitPrice = unitPrice;
	}
}
