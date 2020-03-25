package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import com.lucky.jacklamb.annotation.mvc.LuckyFilter;
import com.lucky.jacklamb.annotation.mvc.LuckyServlet;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.servlet.LuckyDispatherServlet;
import com.lucky.jacklamb.start.FilterMapping;
import com.lucky.jacklamb.start.ServletMapping;
import com.lucky.jacklamb.utils.LuckyUtils;

public class ServerConfig {
	
	public ServerConfig() {
		servletlist=new ArrayList<>();
		listeners=new HashSet<>();
		filterlist=new ArrayList<>();
	}
	
	private static ServerConfig serverConfig;
	
	private int port;
	
	private int closePort;
	
	private String shutdown;
	
	private int sessionTimeout;
	
	public static String projectPath;
	
	private String contextPath;
	
	private String webapp;
	
	private String URIEncoding;
	
	private String docBase;
	
	private String baseDir;
	
	private List<ServletMapping> servletlist;
	
	private List<FilterMapping> filterlist;
	
	private Set<EventListener> listeners;
	
	
	public String getURIEncoding() {
		return URIEncoding;
	}

	public void setURIEncoding(String uRIEncoding) {
		URIEncoding = uRIEncoding;
	}

	public String getDocBase() {
		return docBase;
	}
	
	public int getSessionTimeout() {
		return sessionTimeout;
	}
	
	public int getClosePort() {
		return closePort;
	}
	
	public String getShutdown() {
		return shutdown;
	}

	public String getBaseDir() {
		return baseDir;
	}
	

	/**
	 * ����һ��Tomcat����ʱ�ļ���(�����Ŀ·��)
	 * @param baseDir
	 */
	public void setBaseDir(String baseDir) {
		if(baseDir.startsWith("/")) {
			this.baseDir = projectPath+baseDir.substring(1);
		}else {
			this.baseDir = projectPath+baseDir;
		}
	}
	
	/**
	 * ����һ��Tomcat����ʱ�ļ���(����·��)
	 * @param ap_baseDir
	 */
	public void setApBaseDir(String ap_baseDir) {
		this.baseDir = ap_baseDir;
	}
	

	/**
	 * ����һ�����ڹر�Tomcat�����ָ��
	 * @param shutdown
	 */
	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}

	/**
	 * ����һ�����ڹر�Tomcat����Ķ˿�
	 * @param closePort
	 */
	public void setClosePort(int closePort) {
		this.closePort = closePort;
	}

	/**
	 * ����Session��ʱʱ��
	 * @param sessionTimeout
	 */
	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * ����һ����̬�ļ��Ĵ����(����·��)
	 * @param ap_docBase
	 */
	public void setApDocBase(String ap_docBase) {
		this.docBase = ap_docBase;
	}
	
	/**
	 * ����һ����̬�ļ��Ĵ����(��Ŀ·�������·��)
	 * @param docbase
	 */
	public void setDocBase(String docbase) {
		if(docbase.startsWith("/")) {
			this.docBase=projectPath+docbase.substring(1);
		}else {
			this.docBase=projectPath+docbase;
		}
	}

	public int getPort() {
		return port;
	}

	/**
	 * ����Tomcat�������������˿�
	 * @param port
	 */
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
	
	
	public Set<EventListener> getListeners() {
		return listeners;
	}
	
	public void addListener(EventListener listener) {
		listeners.add(listener);
	}

	public void addServlet(HttpServlet servlet,String...mappings) {
		String servletName=LuckyUtils.TableToClass1(servlet.getClass().getSimpleName());
		Set<String> maps;
		if(mappings.length==0) {
			maps=new HashSet<>();
			maps.add("/"+servletName);
		}else {
			maps=new HashSet<>(Arrays.asList(mappings));
		}
		ServletMapping servletMapping=new ServletMapping(maps,servletName,servlet);
		servletlist.add(servletMapping);
	}
	public List<FilterMapping> getFilterlist() {
		return filterlist;
	}

	public void addFilter(Filter filter,String...mappings) {
		String filterName=LuckyUtils.TableToClass1(filter.getClass().getSimpleName());
		Set<String> maps;
		if(mappings.length==0) {
			maps=new HashSet<>();
			maps.add("/"+filterName);
		}else {
			maps=new HashSet<>(Arrays.asList(mappings));
		}
		FilterMapping filterMapping=new FilterMapping(maps,filterName,filter);
		filterlist.add(filterMapping);
	}
	
	public static ServerConfig defaultServerConfig() {
		if(serverConfig==null) {
			serverConfig=new ServerConfig();
			serverConfig.setPort(8080);
			serverConfig.setClosePort(8005);
			serverConfig.setShutdown("SHUTDOWN");
			serverConfig.setSessionTimeout(30);
			serverConfig.setWebapp("/WebContent/");
			projectPath=System.getProperty("user.dir").replaceAll("\\\\", "/")+"/";
			serverConfig.addServlet(new LuckyDispatherServlet(), "/");
			serverConfig.setContextPath("");
			serverConfig.setBaseDir("Lucky/tomcat/");
			serverConfig.setDocBase("Lucky/project/");
			serverConfig.setURIEncoding("UTF-8");
		}
		return serverConfig;
	}
	
	/**
	 * ע���Servletע��
	 */
	private void servletInit() {
		List<Object> servlets = ApplicationBeans.createApplicationBeans().getBeans(HttpServlet.class);
		ServletMapping servletMap;
		HttpServlet servlet;
		Set<String> smapping;
		LuckyServlet annServlet;
		for(Object servletObj:servlets) {
			servlet=(HttpServlet) servletObj;
			annServlet=servlet.getClass().getAnnotation(LuckyServlet.class);
			smapping=new HashSet<>(Arrays.asList(annServlet.value()));
			servletMap=new ServletMapping(smapping,LuckyUtils.TableToClass1(servlet.getClass().getSimpleName()),servlet);
			servletlist.add(servletMap);
		}
	}
	
	/**
	 * ע���Filterע��
	 */
	private void filterInit() {
		List<Object> filters = ApplicationBeans.createApplicationBeans().getBeans(Filter.class);
		FilterMapping filterMap;
		Filter filter;
		Set<String> fmapping;
		LuckyFilter annFilter;
		for(Object filterObj:filters) {
			filter=(Filter) filterObj;
			annFilter=filter.getClass().getAnnotation(LuckyFilter.class);
			fmapping=new HashSet<>(Arrays.asList(annFilter.value()));
			filterMap=new FilterMapping(fmapping,LuckyUtils.TableToClass1(filter.getClass().getSimpleName()),filter);
			filterlist.add(filterMap);
		}
	}
	
	/**
	 * ע���Listenerע��
	 */
	private void listenerInit() {
		List<Object> listeners = ApplicationBeans.createApplicationBeans().getBeans(EventListener.class);
		listeners.stream().forEach(a->this.listeners.add((EventListener)a));
	}
	
	public void init() {
		listenerInit();
		servletInit();
		filterInit();
	}
}
	
