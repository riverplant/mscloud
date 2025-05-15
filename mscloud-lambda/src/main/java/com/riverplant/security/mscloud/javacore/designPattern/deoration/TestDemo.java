package com.riverplant.security.mscloud.javacore.designPattern.deoration;

public class TestDemo {

	public static void main(String[] args) {
		
		MilkTea milkTea = new MilkTea();
		
		BalTea balTea = new BalTea(milkTea);
		
		System.out.println(balTea.showMaterials());

	}

}
