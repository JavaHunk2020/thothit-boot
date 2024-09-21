package com.techtech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.ProductRepository;
import com.techtech.entity.ProductEntity;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void deleteById(int id) {
		productRepository.deleteById(id);
	}

	public List<ProductEntity> findAll() {
		return productRepository.findAll();
	}

	public void save(ProductEntity entity) {
		productRepository.save(entity);
	}
	
	
	public List<ProductEntity> seachProduct(String stext){
		return productRepository.findByNameContainingOrCategoryContaining(stext, stext);
	}
	

}
