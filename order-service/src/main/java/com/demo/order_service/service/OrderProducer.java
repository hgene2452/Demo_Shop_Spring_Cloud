package com.demo.order_service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.demo.order_service.dto.Field;
import com.demo.order_service.dto.KafkaOrderDto;
import com.demo.order_service.dto.OrderDto;
import com.demo.order_service.dto.Payload;
import com.demo.order_service.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	List<Field> fields = Arrays.asList(
		new Field("string", true, "order_id"),
		new Field("string", true, "user_id"),
		new Field("string", true, "product_id"),
		new Field("int32", true, "qty"),
		new Field("int32", true, "unit_price"),
		new Field("int32", true, "total_price")
	);

	Schema schema = Schema.builder()
		.type("struct")
		.fields(fields)
		.optional(false)
		.name("orders")
		.build();

	public OrderDto send(String kafkaTopic, OrderDto orderDto) {
		Payload payload = Payload.builder()
			.order_id(orderDto.getOrderId())
			.user_id(orderDto.getUserId())
			.product_id(orderDto.getProductId())
			.qty(orderDto.getQty())
			.unit_price(orderDto.getUnitPrice())
			.total_price(orderDto.getTotalPrice())
			.build();

		KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(kafkaOrderDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		kafkaTemplate.send(kafkaTopic, jsonString);
		log.info("Kafka Producer sent data from the Order Service: " + kafkaOrderDto);

		return orderDto;
	}
}
