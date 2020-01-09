package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;

/**
 * ������ǿ��ִ�нڵ�,�ó������������󽫻���һ��������ǿ������
 * @author fk-7075
 *
 */
public abstract class Point {
	
	/**
	 * ��ǰִ�е�Ŀ�귽����ʵ��
	 */
	protected Object target;
	
	/**
	 * ��ǰִ�е�Ŀ�귽����Method����
	 */
	protected Method method;
	
	/**
	 * ��ǰִ�е�Ŀ�귽ִ��ʱ�Ĳ����б�
	 */
	protected Object[] params;
	
	
	/**
	 * ���󷽷������ڲ���һ��������ǿ�������÷�������ʹ��@Aroundע���ע�����ұ�������aspect(������Ϣ)��pointcut(�������Ϣ)<br>
	 * @param chain
	 * @return
	 */
	public abstract Object proceed(Chain chain);

}
