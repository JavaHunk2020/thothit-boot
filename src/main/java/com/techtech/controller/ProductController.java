package com.techtech.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techtech.entity.ProductEntity;
import com.techtech.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/addProduct")
	public String createProduct(HttpServletRequest req) {
		String name = req.getParameter("name");
		String price = req.getParameter("price");
		String category = req.getParameter("category");
		String photo = req.getParameter("photo");

		ProductEntity entity = new ProductEntity();
		entity.setCategory(category);
		entity.setName(name);
		entity.setPhoto(photo);
		entity.setPrice(Double.parseDouble(price));
		entity.setDoe(new Timestamp(new Date().getTime()));
		productService.save(entity);

		// Fetch akk the ProductEntity
		List<ProductEntity> productList = productService.findAll();
		req.setAttribute("productList", productList);
		req.setAttribute("message", "Product is added into database");
		return "addProduct";
	}

	@GetMapping({ "/showAddProduct", "/" })
	public String showAddProductPage(HttpServletRequest req) {
		// I WANT TO SHOW ALL THE PRODUCTS WHEN
		// WE ARE OPENING ADD PRODUCT PAGE
		List<ProductEntity> productList = productService.findAll();
		req.setAttribute("productList", productList);
		return "addProduct";
	}

	@GetMapping("/deleteProduct")
	public String bananana(HttpServletRequest req) {
		String pid = req.getParameter("pid");
		// I WANT TO SHOW ALL THE PRODUCTS WHEN
		// WE ARE OPENING ADD PRODUCT PAGE
		productService.deleteById(Integer.parseInt(pid));

		List<ProductEntity> productList = productService.findAll();
		req.setAttribute("productList", productList);
		req.setAttribute("message", "Product is delete from database");
		return "addProduct";
	}

	@GetMapping("/searchProduct")
	public String searchProduct(@RequestParam String stext, Model model) {
		// I WANT TO SHOW ALL THE PRODUCTS WHEN
		// WE ARE OPENING ADD PRODUCT PAGE
		List<ProductEntity> productList = productService.seachProduct(stext);
		model.addAttribute("productList", productList);
		return "addProduct";
	}

	// <a href="sorting?attribute=name&orderBy=asc">
	@GetMapping("/sorting")
	public String sortingBy(@RequestParam String attribute, @RequestParam String orderBy, Model model) {
		// I WANT TO SHOW ALL THE PRODUCTS WHEN
		// WE ARE OPENING ADD PRODUCT PAGE
		List<ProductEntity> productList = productService.findAll();
		if (attribute.equals("name") && orderBy.equals("asc")) {
			model.addAttribute("orderBy", "desc");
			Collections.sort(productList, Comparator.comparing(ProductEntity::getName));
		} else if (attribute.equals("name") && orderBy.equals("desc")) {
			model.addAttribute("orderBy", "asc");
			Collections.sort(productList, Comparator.comparing(ProductEntity::getName).reversed());
		}

		if (attribute.equals("category") && orderBy.equals("asc")) {
			model.addAttribute("orderBy", "desc");
			Collections.sort(productList, Comparator.comparing(ProductEntity::getCategory));
		} else if (attribute.equals("category") && orderBy.equals("desc")) {
			model.addAttribute("orderBy", "asc");
			Collections.sort(productList, Comparator.comparing(ProductEntity::getCategory).reversed());
		}

		model.addAttribute("productList", productList);
		return "addProduct";
	}

	@GetMapping("/api/chart")
	public ResponseEntity<byte[]> getBarChart() throws IOException {
		
		
		byte[] chartImage = generateBarChart();

		// Set HTTP Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(chartImage.length);

		return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
	}

	// Generate the bar chart
	public byte[] generateBarChart() throws IOException {
		// Create dataset with String X-axis and double Y-axis
		CategoryDataset dataset = createDataset();
		// Create a bar chart
		JFreeChart chart = ChartFactory.createBarChart("Sales by Product", // Chart Title
				"Product", // X-axis label (String)
				"Sales (in $)", // Y-axis label (Double)
				dataset);
		
		
		// Step 3: Customize the Bar Renderer
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // Set different colors for each bar
        renderer.setSeriesPaint(0, Color.ORANGE);
        renderer.setSeriesPaint(1, Color.MAGENTA);

		// Convert the chart to an image (PNG)
		BufferedImage chartImage = chart.createBufferedImage(800, 600);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(chartImage, "png", baos);
		return baos.toByteArray();
	}

	// Create dataset with String as X-axis values and Double as Y-axis values
	private CategoryDataset createDataset() {
		
		//Mobile, 12
		//Mobile 8
		//Mobile, 4
		
		//T-Shirt -12
		
		//map = {Mobile,24}
		
		List<ProductEntity> productList = productService.findAll();
		Map<String,Double> map =new HashMap<>();
		for(ProductEntity pe :productList){
			  String category=pe.getCategory();
			  double currentPrice =pe.getPrice();
			  if(map.containsKey(category)) {
				  double priceInMap=map.get(category);
				  map.put(category, priceInMap+currentPrice);
			  }else {
				  map.put(category, currentPrice);
			  }
		}
	
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// Add data (Product name as X-axis and Sales value as Y-axis)
		map.forEach((category,price)->{
			dataset.addValue(price, "Sales", category); // X-axis: Product A, Y-axis: 25000.0
		});
		return dataset;
	}

}
