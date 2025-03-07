package com.riverplant.mscloud.javacore.designPattern.proxy;

public class TestServiceProxy implements TestService {

	private final TestService service;
	
	private final int state;
	
	
	public TestServiceProxy(TestService service, int state) {
		this.service = service;
		this.state = state;
	}


	@Override
	public void doSomeThing() {
		System.out.println("start....");
		if(state == 1) 
		   service.doSomeThing();
		else 
			System.out.println("state is 0");

	}

}
