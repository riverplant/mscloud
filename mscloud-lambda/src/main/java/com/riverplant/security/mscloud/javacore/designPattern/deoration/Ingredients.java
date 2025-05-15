package com.riverplant.security.mscloud.javacore.designPattern.deoration;

public abstract class Ingredients extends Product {

	protected Product product;
	
	public Ingredients(Product product) {
		this.product = product;
	}

	@Override
	public double totalPrice() {
		
		return product.totalPrice();
	}

	@Override
	public String showMaterials() {
		// TODO Auto-generated method stub
		return product.showMaterials();
	}

}
