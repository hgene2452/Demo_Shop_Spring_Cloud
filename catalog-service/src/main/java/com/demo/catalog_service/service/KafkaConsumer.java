package com.demo.catalog_service.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.demo.catalog_service.entity.Catalog;
import com.demo.catalog_service.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

	private final CatalogRepository catalogRepository;

	@KafkaListener(topics = "example-catalog-topic")
	public void processMessage(String kafkaMessage) {
		log.info("Kafka Message: " + kafkaMessage);

		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		Catalog catalog = catalogRepository.findByProductId((String)map.get("productId"))
			.orElseThrow(() -> new IllegalArgumentException(""));
		catalog.setStock(catalog.getStock() - (Integer) map.get("qty"));

		catalogRepository.save(catalog);
	}
}
