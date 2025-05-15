package com.riverplant.security.mscloud.javacore.designPattern.observer;

public class Children implements Observer {

	@Override
	public void update(String message) {
		System.out.println("兒女收到新消息:	" + message);

	}

}
