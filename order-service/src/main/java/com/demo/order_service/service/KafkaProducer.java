package com.demo.order_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.demo.order_service.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public OrderDto send(String kafkaTopic, OrderDto orderDto) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(orderDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		kafkaTemplate.send(kafkaTopic, jsonString);
		log.info("Kafka Producer sent data from the Order Service: " + orderDto);

		return orderDto;
	}
}
