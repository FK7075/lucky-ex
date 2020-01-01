package com.lucky.jacklamb.aop.proxy;

import java.util.List;

import net.sf.cglib.proxy.Enhancer;

public class ProxyFactory {
	
	private static ProxyFactory proxyFactory;
	
	private ProxyFactory() {}
	
	public static ProxyFactory createProxyFactory() {
		if(proxyFactory==null)
			proxyFactory=new ProxyFactory();
		return proxyFactory;
	}
	
	/**
	 * �õ�һ���������
	 * @param targetClass ��ʵ���Class
	 * @param restPoints ��ǿPoints(�ɱ����ʽ)
	 * @return
	 */
	public Object getProxy(Class<?> targetClass,PointRun...pointRuns) {
		final Enhancer en=new Enhancer();
		en.setSuperclass(targetClass);
		en.setCallback(new LuckyMethodInterceptor(pointRuns));
		return en.create();
	}
	
	/**
	 * �õ�һ���������
	 * @param targetClass ��ʵ���Class
	 * @param restPoints ��ǿPoints(���ϲ���ʽ)
	 * @return
	 */
	public Object getProxy(Class<?> targetClass,List<PointRun> pointRuns) {
		final Enhancer en=new Enhancer();
		en.setSuperclass(targetClass);
		en.setCallback(new LuckyMethodInterceptor(pointRuns));
		return en.create();
	}

}
