package com.riverplant.mscloud.javacore.designPattern.factory;

public class BikeFactory implements VehicleFactory {

	@Override
	public Vehicle getVehicle() {
		
		return new Bike();
	}

}
