package com.lucky.jacklamb.aop.expand;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

/**
 * 增强执行链
 * @author DELL
 *
 */
public class LuckyChain {
	
	/**
	 * 单点增强集合
	 */
	private Stack<LuckyPoint> stack;
	
	/**
	 * 目标执行项
	 */
	private Perform target;
	
	
	public LuckyChain(Stack<LuckyPoint> stack, Perform target) {
		this.stack = stack;
		this.target = target;
	}


	public Object proceed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result;
		if(stack.isEmpty()) {
			result=target.getMethod().invoke(target.getExpand(), target.getParams());
		}else {
			LuckyPoint point=stack.pop();
			result=point.proceed(this);
		}
		
		return result;
	}

}
