package com.techtech.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.ProductImageRepository;
import com.techtech.dao.ProductRepository;
import com.techtech.dto.ProductDTO;
import com.techtech.entity.ProductEntity;
import com.techtech.entity.ProductImage;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ProductImageRepository productImageRepository;
	
	public byte[] findProductImageById(long piid) {
		 Optional<ProductImage> optional=productImageRepository.findById(piid);
		 return optional.isPresent()?optional.get().getPhoto():new byte[] {};
	}
	
	
	public void saveImage(String title,byte[] photo,long pid) {
		ProductImage productImage=new ProductImage();
		productImage.setCreateddate(new Timestamp(new Date().getTime()));
		productImage.setTitle(title);
		productImage.setPhoto(photo);
		ProductEntity productEntity=productRepository.getById((int)pid);
		productImage.setProduct(productEntity);
		productImageRepository.save(productImage);
	}

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
			List<ProductImage> images=entity.getImages();
			if(images!=null) {
				for(ProductImage pi : images) {
					dto.getImageIds().add(pi.getId());
				}
			}
			dtos.add(dto);
		}
		return dtos;
	}
}
