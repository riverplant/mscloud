package com.riverplant.mscloud.javacore.designPattern.proxy.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DynamicProxyDemo {

	public static void main(String[] args) {
		String s1 = "aaaa";
		s1="bbbb";
		AdminService service = new AdminServiceImpl();
		
		AdminService  proxy = DynamicProxyTemplate.createProxy(service, 1);
		proxy.adminOperation();
		
		List<Integer> list = Collections.synchronizedList(new ArrayList<>());
	}

}
