package com.demo.catalog_service.service;

import java.util.List;

import com.demo.catalog_service.dto.CatalogDto;

public interface CatalogService {
	List<CatalogDto> findAllCatalog();
}
