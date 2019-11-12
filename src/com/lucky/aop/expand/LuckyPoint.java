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
	private ExpandPrefixSuffix epf;
	
	
	public LuckyPoint(ExpandPrefixSuffix epf) {
		this.epf = epf;
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
		Perform prefixExpand = epf.getPrefixExpand();
		Perform suffixExpand = epf.getSuffixExpand();
		if(prefixExpand!=null)//ִ��ǰ����ǿ��
			prefixExpand.getMethod().invoke(prefixExpand.getExpand(), prefixExpand.getParams());
		result=chain.proceed();
		if(suffixExpand!=null)//ִ�к�����ǿ��
			suffixExpand.getMethod().invoke(suffixExpand.getExpand(), suffixExpand.getParams());
		return result;
	}
	

}
