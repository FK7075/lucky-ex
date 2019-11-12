package com.lucky.aop.expand;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LuckyInterceptor implements MethodInterceptor {
	
	private LuckyChain chain;
	
	public LuckyInterceptor(LuckyChain chain) {
		this.chain = chain;
	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		return chain.proceed();
	}

}
