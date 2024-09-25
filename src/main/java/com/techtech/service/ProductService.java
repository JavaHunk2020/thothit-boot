package com.techtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.ProductRepository;
import com.techtech.dto.ProductDTO;
import com.techtech.entity.ProductEntity;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void deleteById(int id) {
		productRepository.deleteById(id);
	}

	public List<ProductDTO> findAll() {
		List<ProductEntity>  listEntity=productRepository.findAll();
		return convertEntityDTO(listEntity);
	}

	public void save(ProductDTO productDTO) {
		ProductEntity entity=new ProductEntity();
		BeanUtils.copyProperties(productDTO, entity);
		productRepository.save(entity);
	}
	
	
	public List<ProductDTO> seachProduct(String stext){
		List<ProductEntity>  listEntity=productRepository.findByNameContainingOrCategoryContaining(stext, stext);
		return convertEntityDTO(listEntity);
	}
	
	private List<ProductDTO> convertEntityDTO(List<ProductEntity>  listEntity){
		List<ProductDTO> dtos=new ArrayList<>();
		for(ProductEntity entity: listEntity) {
			ProductDTO dto =new ProductDTO();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return dtos;
	}
}
