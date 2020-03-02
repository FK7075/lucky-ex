package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import com.lucky.jacklamb.servlet.LuckyDispatherServlet;
import com.lucky.jacklamb.start.FilterMapping;
import com.lucky.jacklamb.start.ServletMapping;
import com.lucky.jacklamb.utils.LuckyUtils;

public class ServerConfig {
	
	private static ServerConfig serverConfig;
	
	private int port;
	
	private int closePort;
	
	private String shutdown;
	
	private int sessionTimeout;
	
	public static String projectPath;
	
	private String contextPath;
	
	private String webapp;
	
	private String docBase;
	
	private String baseDir;
	
	private List<ServletMapping> servletlist;
	
	private List<FilterMapping> filterlist;
	
	private Set<EventListener> listeners;
	
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
	 * 设置一个Tomcat的临时文件夹(相对项目路径)
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
	 * 设置一个Tomcat的临时文件夹(绝对路径)
	 * @param ap_baseDir
	 */
	public void setApBaseDir(String ap_baseDir) {
		this.baseDir = ap_baseDir;
	}
	

	/**
	 * 设置一个用于关闭Tomcat服务的指令
	 * @param shutdown
	 */
	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}

	/**
	 * 设置一个用于关闭Tomcat服务的端口
	 * @param closePort
	 */
	public void setClosePort(int closePort) {
		this.closePort = closePort;
	}

	/**
	 * 设置Session超时时间
	 * @param sessionTimeout
	 */
	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * 设置一个静态文件的储存库(绝对路径)
	 * @param ap_docBase
	 */
	public void setApDocBase(String ap_docBase) {
		this.docBase = ap_docBase;
	}
	
	/**
	 * 设置一个静态文件的储存库(项目路径的相对路径)
	 * @param docbase
	 */
	public void setDocBase(String docbase) {
		if(docbase.startsWith("/")) {
			this.docBase=projectPath+docbase.substring(1);
		}else {
			this.docBase=projectPath+docbase;
		}
	}

	public ServerConfig() {
		servletlist=new ArrayList<>();
		filterlist=new ArrayList<>();
		listeners=new HashSet<>();
	}
	
	public int getPort() {
		return port;
	}

	/**
	 * 设置Tomcat服务器的启动端口
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
		}
		return serverConfig;
	}
}
	
