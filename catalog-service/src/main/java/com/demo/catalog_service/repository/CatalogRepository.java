package com.demo.catalog_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.catalog_service.dto.CatalogDto;
import com.demo.catalog_service.entity.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
	@Query("SELECT new com.demo.catalog_service.dto.CatalogDto(c.productId, c.productName, c.stock, c.unitPrice) "
		+ "FROM Catalog c")
	List<CatalogDto> findAllCatalogDto();
}
