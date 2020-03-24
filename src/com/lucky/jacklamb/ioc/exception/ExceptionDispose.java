package com.lucky.jacklamb.ioc.exception;

import java.lang.reflect.Method;

import com.lucky.jacklamb.servlet.Model;

public abstract class ExceptionDispose {
	
	/**
	 * Model����
	 */
	protected Model model;
	
	
	/**
	 * ��ǰ������Ӧ��Controller����
	 */
	protected Object controllerObj;

	/**
	 * ��ǰ������Ӧ��Controller�����Class����
	 */
	protected Class<?> currClass;

	/**
	 * ��ǰ������Ӧ��Controller����
	 */
	protected Method currMethod;
	
	/**
	 * ��ǰ������Ӧ��Controller��������
	 */
	protected Object[] params;
	
	
	
	public void init(Model model, Object controllerObj, Class<?> currClass,
			Method currMethod, Object[] params) {
		this.model = model;
		this.controllerObj = controllerObj;
		this.currClass = currClass;
		this.currMethod = currMethod;
		this.params = params;
	}



	/**
	 * �쳣����
	 */
	public abstract void dispose(Throwable e);
	

}
