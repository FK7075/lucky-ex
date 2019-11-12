package com.lucky.aop.expand;

@FunctionalInterface
public interface Point {
	
	public Object proceed(LuckyChain chain);

}
