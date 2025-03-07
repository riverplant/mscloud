package com.riverplant.mscloud.javacore.designPattern.observer;

public class Doctor implements Observer {

	@Override
	public void update(String message) {
		System.out.println("醫生收到消息:"+ message);
	}

}
