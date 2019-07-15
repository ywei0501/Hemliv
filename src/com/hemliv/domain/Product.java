package com.hemliv.domain;

public class Product {

	private String proId;
	private String proName;
	private String proFormat;
	private Integer proPrice;
	private String proImage;
	private String prodesc;
	private ProductSort productsort;

	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProFormat() {
		return proFormat;
	}
	public void setProFormat(String proFormat) {
		this.proFormat = proFormat;
	}
	public Integer getProPrice() {
		return proPrice;
	}
	public void setProPrice(Integer proPrice) {
		this.proPrice = proPrice;
	}
	
	public String getProImage() {
		return proImage;
	}
	public void setProImage(String proImage) {
		this.proImage = proImage;
	}
	public String getProdesc() {
		return prodesc;
	}
	public void setProdesc(String prodesc) {
		this.prodesc = prodesc;
	}
	public ProductSort getProductsort() {
		return productsort;
	}
	public void setProductsort(ProductSort productsort) {
		this.productsort = productsort;
	}
	
	
	
	
}
