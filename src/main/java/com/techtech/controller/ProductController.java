package com.techtech.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.techtech.dao.ProductRepository;
import com.techtech.entity.ProductEntity;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
    @GetMapping("/deleteProduct")
	public String bananana(HttpServletRequest req) {
		String pid=req.getParameter("pid");
		
		 //I WANT TO SHOW ALL THE PRODUCTS WHEN
		 //WE ARE OPENING ADD PRODUCT PAGE
		 productRepository.deleteById(Integer.parseInt(pid));
		 
		 List<ProductEntity> productList=productRepository.findAll();
		 req.setAttribute("productList",productList);
		  req.setAttribute("message","Product is delete from database");
		 return "addProduct";
	}
	
	
	@GetMapping({"/showAddProduct","/"})
	public String showAddProductPage(HttpServletRequest req) {
		 //I WANT TO SHOW ALL THE PRODUCTS WHEN
		 //WE ARE OPENING ADD PRODUCT PAGE
		 List<ProductEntity> productList=productRepository.findAll();
		 req.setAttribute("productList",productList);
		 return "addProduct";
	}
	
	@PostMapping("/addProduct")
	public String createProduct(HttpServletRequest req) {
		  String name=req.getParameter("name");
		  String price=req.getParameter("price");
		  String category=req.getParameter("category");
		  String photo=req.getParameter("photo");
		  
		  ProductEntity entity=new ProductEntity();
		  entity.setCategory(category);
		  entity.setName(name);
		  entity.setPhoto(photo);
		  entity.setPrice(Double.parseDouble(price));
		  entity.setDoe(new Timestamp(new Date().getTime()));
		  productRepository.save(entity);
		  
		  //Fetch akk the ProductEntity
		  List<ProductEntity> productList=productRepository.findAll();
		  req.setAttribute("productList",productList);
		  req.setAttribute("message","Product is added into database");
		 return "addProduct";
	}
	
	

}
