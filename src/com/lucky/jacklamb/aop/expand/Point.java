package com.lucky.jacklamb.aop.expand;

import java.lang.reflect.InvocationTargetException;

@FunctionalInterface
public interface Point {
	
	public Object proceed(LuckyChain chain) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
