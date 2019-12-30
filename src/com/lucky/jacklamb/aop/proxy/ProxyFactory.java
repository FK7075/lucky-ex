package com.lucky.jacklamb.aop.proxy;

import java.util.List;

import net.sf.cglib.proxy.Enhancer;

public class ProxyFactory {
	
	private ProxyFactory() {}
	
	public static ProxyFactory createProxyFactory() {
		return new ProxyFactory();
	}
	
	/**
	 * 
	 * @param targetClass 真实类的Class
	 * @param performMethods 该类的所有增强方法
	 * @param restPoints 增强Points
	 * @return
	 */
	public Object getProxy(Class<?> targetClass,List<PerformMethod> performMethods,PointRun...pointRuns) {
		final Enhancer en=new Enhancer();
		en.setSuperclass(targetClass);
		en.setCallback(new LuckyMethodInterceptor(performMethods,pointRuns));
		return en.create();
	}

}
