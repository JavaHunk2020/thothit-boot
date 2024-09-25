package com.techtech.dto;

import java.sql.Timestamp;

public class ProductDTO {

	private int pid;
	private String name;
	private String category;
	private double price;
	private String photo;
	private Timestamp doe;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Timestamp getDoe() {
		return doe;
	}

	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}

	@Override
	public String toString() {
		return "ProductDTO [pid=" + pid + ", name=" + name + ", category=" + category + ", price=" + price + ", photo="
				+ photo + ", doe=" + doe + "]";
	}

}
