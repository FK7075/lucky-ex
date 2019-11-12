package com.lucky.aop.expand;

import java.lang.reflect.Method;

public class Perform {
	
	private Object expand;
	
	private Method method;
	
	private Object[] params;

	public Object getExpand() {
		return expand;
	}

	public void setExpand(Object expand) {
		this.expand = expand;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	

}
