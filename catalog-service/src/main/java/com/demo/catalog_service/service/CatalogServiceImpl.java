package com.demo.catalog_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.catalog_service.dto.CatalogDto;
import com.demo.catalog_service.repository.CatalogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

	private final CatalogRepository catalogRepository;

	@Override
	public List<CatalogDto> findAllCatalog() {
		return catalogRepository.findAllCatalogDto();
	}
}
