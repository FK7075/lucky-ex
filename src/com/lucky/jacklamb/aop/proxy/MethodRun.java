package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lucky.jacklamb.enums.Location;

public class MethodRun {
	
	private Object expand;//增强类对象
	
	private Method method;//增强方法
	
	private Object[] params;//执行增强方法需要的参数
	
	private Location location;//增强方式
	
	public MethodRun(Object expand, Method method, Object[] params, Location location) {
		this.expand = expand;
		this.method = method;
		this.params = params;
		this.location = location;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * 执行增强方法
	 * @return
	 */
	public Object methodRun() {
		try {
			return method.invoke(expand, params);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("IllegalAccessException", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("IllegalArgumentException", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("InvocationTargetException", e);
		}
	}

}
