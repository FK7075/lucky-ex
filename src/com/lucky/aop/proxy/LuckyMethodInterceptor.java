package com.lucky.aop.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LuckyMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object object, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
