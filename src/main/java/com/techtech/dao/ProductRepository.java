package com.techtech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techtech.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	
	public List<ProductEntity> findByNameContainingOrCategoryContaining(String name, String category);
}
