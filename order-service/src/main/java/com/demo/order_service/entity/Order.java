package com.demo.order_service.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120, unique = true)
	private String productId;

	@Column(nullable = false)
	private Integer qty;

	@Column(nullable = false)
	private Integer unitPrice;

	@Column(nullable = false)
	private Integer totalPrice;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, unique = true)
	private String orderId;

	@Column(nullable = false, updatable = false, insertable = false)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date createdAt;

	@Builder
	public Order(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String userId, String orderId) {
		this.productId = productId;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.userId = userId;
		this.orderId = orderId;
	}
}
