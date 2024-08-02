package com.demo.catalog_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.catalog_service.dto.CatalogDto;
import com.demo.catalog_service.service.CatalogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

	private final CatalogService catalogService;

	@GetMapping("/catalogs")
	public ResponseEntity<List<CatalogDto>> findAllCatalog() {
		List<CatalogDto> catalogDtos = catalogService.findAllCatalog();
		return ResponseEntity.ok(catalogDtos);
	}
}
