package com.lucky.jacklamb.aop.expand;

import java.lang.reflect.Method;

public class Perform {
	
	private String enhance;
	
	private Object expand;
	
	private Method method;
	
	private Object[] params;
	

	public String getEnhance() {
		return enhance;
	}

	public void setEnhance(String enhance) {
		this.enhance = enhance;
	}

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
