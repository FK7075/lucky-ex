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
	private ExpandPrefixSuffix epf;
	
	
	public LuckyPoint(ExpandPrefixSuffix epf) {
		this.epf = epf;
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
		Perform prefixExpand = epf.getPrefixExpand();
		Perform suffixExpand = epf.getSuffixExpand();
		if(prefixExpand!=null)//执行前置增强项
			prefixExpand.getMethod().invoke(prefixExpand.getExpand(), prefixExpand.getParams());
		result=chain.proceed();
		if(suffixExpand!=null)//执行后置增强项
			suffixExpand.getMethod().invoke(suffixExpand.getExpand(), suffixExpand.getParams());
		return result;
	}
	

}
