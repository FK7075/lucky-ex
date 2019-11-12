package com.lucky.aop.expand;

import java.util.Stack;

import net.sf.cglib.proxy.Enhancer;

public class ProxyFactory {
	
	private ProxyFactory() {};
	
	public static ProxyFactory create() {
		return new ProxyFactory();
	}
	
	public Object getProxyObject(Perform target, Stack<LuckyPoint> stack) {
		final Enhancer en=new Enhancer();
		en.setSuperclass(target.getExpand().getClass());
		LuckyChain chain=new LuckyChain(stack, target);
		LuckyInterceptor interceptor=new LuckyInterceptor(chain);
		en.setCallback(interceptor);
		return en.create();
	}

}
