package com.lucky.jacklamb.aop.proxy;

import net.sf.cglib.proxy.Enhancer;

public class CreateProxyObject {
	
	private LuckyMethodInterceptor intercept;
	
	private LuckyCallbackFilter filter;
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> clzz) {
		Enhancer enhancer=new Enhancer();
		enhancer.setSuperclass(clzz);
		enhancer.setCallback(intercept);
		enhancer.setCallbackFilter(filter);
		return (T) enhancer.create();
	}

}
