package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;

/**
 * ������ǿ��ִ�нڵ�,�ó������������󽫻���һ��������ǿ������
 * @author fk-7075
 *
 */
public abstract class Point {
	
	/**
	 * ��ǰִ�е�Ŀ�귽���Ĵ���ʵ��
	 */
	protected Object aspectObject;
	
	/**
	 * Ŀ������Class
	 */
	protected Class<?> targetClass;
	
	/**
	 * ��ǰִ�е�Ŀ�귽����Method����
	 */
	protected Method method;
	
	/**
	 * ��ǰִ�е�Ŀ�귽ִ��ʱ�Ĳ����б�
	 */
	protected Object[] params;
	
	/**
	 * ��ǰ������ǩ����Ϣ
	 */
	protected TargetMethodSignature targetMethodSignature;
	
	protected void init(TargetMethodSignature targetMethodSignature) {
		this.targetMethodSignature=targetMethodSignature;
		aspectObject=targetMethodSignature.getAspectObject();
		method=targetMethodSignature.getCurrMethod();
		params=targetMethodSignature.getParams();
		targetClass=targetMethodSignature.getTargetClass();
	}
	
	
	/**
	 * ���󷽷������ڲ���һ��������ǿ�������÷�������ʹ��@Aroundע���ע�����ұ�������aspect(������Ϣ)��pointcut(�������Ϣ)<br>
	 * @param chain
	 * @return
	 */
	public abstract Object proceed(Chain chain);

}
