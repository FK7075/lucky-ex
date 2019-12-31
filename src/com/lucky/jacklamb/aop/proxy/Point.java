package com.lucky.jacklamb.aop.proxy;

import java.lang.reflect.Method;

public abstract class Point {
	
	protected Method method;
	
	protected Object[] params;
	
	public abstract Object proceed(Chain chain);

}
