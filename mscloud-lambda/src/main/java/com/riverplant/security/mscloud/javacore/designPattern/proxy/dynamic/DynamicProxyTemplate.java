package com.riverplant.security.mscloud.javacore.designPattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTemplate implements InvocationHandler {

	private final Object target;
	
	private final int state;
	
	
	
	public DynamicProxyTemplate(Object target, int state) {
		this.target = target;
		this.state = state;
	}


    /**
     * proxy: 代理類對象
     * method： 要調用的方法
     * args： 該方法需要的參數
     */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//前置處理，例如開始事務
		//使用反射
		return state == 1 ? method.invoke(target, args) : null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(T target, int state) {
		return (T) Proxy.newProxyInstance(
				target.getClass().getClassLoader(),  //被代理對象的類
				target.getClass().getInterfaces(),   //被代理對象實現的接口
				new DynamicProxyTemplate(target, state)); //根據什麽模板實現代理類
	}

}
