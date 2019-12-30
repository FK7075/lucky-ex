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
	 * @param targetClass ��ʵ���Class
	 * @param performMethods �����������ǿ����
	 * @param restPoints ��ǿPoints
	 * @return
	 */
	public Object getProxy(Class<?> targetClass,List<PerformMethod> performMethods,PointRun...pointRuns) {
		final Enhancer en=new Enhancer();
		en.setSuperclass(targetClass);
		en.setCallback(new LuckyMethodInterceptor(performMethods,pointRuns));
		return en.create();
	}

}
