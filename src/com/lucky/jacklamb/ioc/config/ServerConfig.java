package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import com.lucky.jacklamb.servlet.LuckyDispatherServlet;
import com.lucky.jacklamb.start.FilterMapping;
import com.lucky.jacklamb.start.ServletMapping;

public class ServerConfig {
	
	private static ServerConfig serverConfig;
	
	private int port;
	
	private String contextPath;
	
	private String webapp;
	
	private List<ServletMapping> servletlist;
	
	private List<FilterMapping> filterlist;
	
	public ServerConfig() {
		servletlist=new ArrayList<>();
		filterlist=new ArrayList<>();
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getWebapp() {
		return webapp;
	}

	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}
	
	public List<ServletMapping> getServletlist() {
		return servletlist;
	}

	public void addServlet(String servletName,String requestMapping,HttpServlet servlet) {
		ServletMapping servletMapping=new ServletMapping(requestMapping,servletName,servlet);
		servletlist.add(servletMapping);
	}
	
	public List<FilterMapping> getFilterlist() {
		return filterlist;
	}

	public void addFilterlist(String filtertName,String requestMapping,Filter filter) {
		FilterMapping filterMapping=new FilterMapping(requestMapping,filtertName,filter);
		filterlist.add(filterMapping);
	}
	
	public static ServerConfig defaultServerConfig() {
		if(serverConfig==null) {
			serverConfig=new ServerConfig();
			serverConfig.setPort(8080);
			serverConfig.setContextPath("");
			serverConfig.setWebapp("/WebContent/");
			serverConfig.addServlet("lucky", "/", new LuckyDispatherServlet());
		}
		return serverConfig;
	}
}
	
