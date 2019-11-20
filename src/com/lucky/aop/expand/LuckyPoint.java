package com.lucky.aop.expand;

import java.lang.reflect.InvocationTargetException;

/**
 * ������ǿִ����
 * @author DELL
 *
 */
public class LuckyPoint {
	
	/**
	 * ǰ����ǿ��ִ����
	 */
	private Perform perform;
	
	
	public LuckyPoint(Perform perform) {
		this.perform = perform;
	}

	/**
	 * ִ����ǿ
	 * @param chain ��ǿ��
	 * @return 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object proceed(LuckyChain chain) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result;
		if("before".equalsIgnoreCase(perform.getEnhance()))//ִ��ǰ����ǿ��
			perform.getMethod().invoke(perform.getExpand(), perform.getParams());
		result=chain.proceed();
		if("after".equalsIgnoreCase(perform.getEnhance()))//ִ�к�����ǿ��
			perform.getMethod().invoke(perform.getExpand(), perform.getParams());
		return result;
	}
	

}
