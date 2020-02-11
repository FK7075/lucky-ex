package com.lucky.jacklamb.ioc.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	private int sessionTimeout;
	
	private boolean defa=true;
	
	public static String projectPath;
	
	private static boolean first=true;
	
	private String contextPath;
	
	private String webapp;
	
	private String docBase;
	
	private List<ServletMapping> servletlist;
	
	private List<FilterMapping> filterlist;
	
	public String getDocBase() {
		if(first) {
			if(defa) {
				docBase=projectPath+"tomcat."+serverConfig.getPort()+"/work/Tomcat/localhost/Lucky/";
			}
			File doc=new File(docBase);
			if(!doc.isDirectory())
				doc.mkdirs();
			first=false;
		}
		return docBase;
	}
	
	public int getSessionTimeout() {
		return sessionTimeout;
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
	 * @param docBase
	 */
	public void setDocBase(String docBase) {
		if(defa)
			defa=false;
		this.docBase = docBase;
	}
	
	/**
	 * ����һ����̬�ļ��Ĵ����(��Ŀ·�������·��)
	 * @param relativeProjectPath
	 */
	public void setDocBaseRelativeProject(String relativeProjectPath) {
		if(defa)
			defa=false;
		this.docBase=projectPath+relativeProjectPath;
	}

	public ServerConfig() {
		servletlist=new ArrayList<>();
		filterlist=new ArrayList<>();
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
			serverConfig.setSessionTimeout(30);
			serverConfig.setContextPath("");
			serverConfig.setWebapp("/WebContent/");
			if(ServerConfig.class.getResource("/")!=null) {
				String path=ServerConfig.class.getResource("/").getPath();
				path=path.replaceAll("/bin/", "/");
				projectPath=path;
			}else {
				String path=ServerConfig.class.getResource("").getPath();
				path=path.substring(6,path.indexOf(".jar!"));
				path=path.substring(0,path.lastIndexOf("/"))+"/";
				projectPath=path;
			}
			serverConfig.addServlet(new LuckyDispatherServlet(), "/");
		}
		return serverConfig;
	}
}
	
