package com.lucky.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@SuppressWarnings("all")
public class LuckyWebContext {
	
	private static final ThreadLocal<LuckyWebContext> context=new ThreadLocal<LuckyWebContext>();
	
	public static LuckyWebContext getCurrentContext() {
		return (LuckyWebContext) context.get();
	}

	public static LuckyWebContext createContext() {
		return new LuckyWebContext();
	}
	
	public static void setContext(LuckyWebContext context1) {
		context.set(context1);
	}
	
	public static void clearContext() {
		context.set(null);
	}
	
	private HttpServletRequest request=null;
	private HttpServletResponse response=null;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
}
