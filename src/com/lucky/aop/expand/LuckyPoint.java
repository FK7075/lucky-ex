package com.lucky.aop.expand;

import java.lang.reflect.InvocationTargetException;

/**
 * 单点增强执行项
 * @author DELL
 *
 */
public class LuckyPoint {
	
	/**
	 * 前后增强的执行项
	 */
	private Perform perform;
	
	
	public LuckyPoint(Perform perform) {
		this.perform = perform;
	}

	/**
	 * 执行增强
	 * @param chain 增强链
	 * @return 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object proceed(LuckyChain chain) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result;
		if("before".equalsIgnoreCase(perform.getEnhance()))//执行前置增强项
			perform.getMethod().invoke(perform.getExpand(), perform.getParams());
		result=chain.proceed();
		if("after".equalsIgnoreCase(perform.getEnhance()))//执行后置增强项
			perform.getMethod().invoke(perform.getExpand(), perform.getParams());
		return result;
	}
	

}
