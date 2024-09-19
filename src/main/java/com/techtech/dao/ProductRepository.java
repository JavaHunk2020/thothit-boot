package com.techtech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techtech.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

}
