package com.lucky.jacklamb.start;

import javax.servlet.http.HttpServlet;

public class ServletMapping {
	
	private String requestMapping;
	
	private String servletName;
	
	private HttpServlet servlet;
	
	public ServletMapping(String requestMapping, String servletName, HttpServlet servlet) {
		this.requestMapping = requestMapping;
		this.servletName = servletName;
		this.servlet = servlet;
	}
	
	public String getRequestMapping() {
		return requestMapping;
	}
	
	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}
	
	public String getServletName() {
		return servletName;
	}
	
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	
	public HttpServlet getServlet() {
		return servlet;
	}
	
	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;
	}
	

}
