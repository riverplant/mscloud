package com.riverplant.mscloud.javacore.designPattern.deoration;

public class BalTea extends Ingredients {

	public BalTea(Product product) {
		super(product);	
	}

	@Override
	public double totalPrice() {
		// TODO Auto-generated method stub
		return super.totalPrice() + 3;
	}

	@Override
	public String showMaterials() {
		// TODO Auto-generated method stub
		return super.showMaterials() + ", 珍珠";
	}
	
	

}
