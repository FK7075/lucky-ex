package com.lucky.jacklamb.ioc.exception;

import com.lucky.jacklamb.servlet.Model;

@FunctionalInterface
public interface ExceptionDispose {
	
	public void dispose(Model model,Throwable e);

}
