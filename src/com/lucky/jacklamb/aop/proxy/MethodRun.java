package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lucky.jacklamb.enums.Location;

public class MethodRun {
	
	private Object expand;//��ǿ�����
	
	private Method method;//��ǿ����
	
	private Object[] params;//ִ����ǿ������Ҫ�Ĳ���
	
	private Location location;//��ǿ��ʽ
	
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
	 * ִ����ǿ����
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
