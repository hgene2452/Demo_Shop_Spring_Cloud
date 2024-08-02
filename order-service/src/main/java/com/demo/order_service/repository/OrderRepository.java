package com.demo.order_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.order_service.dto.OrderDto;
import com.demo.order_service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT new com.demo.order_service.dto.OrderDto(o.productId, o.qty, o.unitPrice, o.totalPrice, o.orderId, o.userId) "
		+ "FROM Order o "
		+ "WHERE o.userId = :userId")
	List<OrderDto> findAllOrderDtoByUserId(@Param("userId") String userId);

	@Query("SELECT new com.demo.order_service.dto.OrderDto(o.productId, o.qty, o.unitPrice, o.totalPrice, o.orderId, o.userId) "
		+ "FROM Order o "
		+ "WHERE o.orderId = :orderId")
	Optional<OrderDto> findOrderDtoByOrderId(@Param("orderId") String orderId);
}
