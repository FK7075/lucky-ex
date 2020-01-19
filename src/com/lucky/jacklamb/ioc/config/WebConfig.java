package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebConfig {
	
	private static WebConfig webConfig;
	
	private String encoding;
	
	private Map<String,String> staticHander;
	
	private List<String> handerPrefixAndSuffix;
	
	private boolean openStaticResourceManage;
	
	private boolean postChangeMethod;
	
	
	private WebConfig() {
		encoding="ISO-8859-1";
		openStaticResourceManage=false;
		postChangeMethod=false;
		handerPrefixAndSuffix=new ArrayList<>();;
		handerPrefixAndSuffix.add("");handerPrefixAndSuffix.add("");
		staticHander=new HashMap<>();
	}
	
	public static WebConfig defauleWebConfig() {
		if(webConfig==null)
			webConfig=new WebConfig();
		return webConfig;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public boolean isOpenStaticResourceManage() {
		return openStaticResourceManage;
	}

	/**
	 * 是否开启静态文件处理器(默认关闭 false)
	 * @param openStaticResourceManage
	 */
	public void openStaticResourceManage(boolean openStaticResourceManage) {
		this.openStaticResourceManage = openStaticResourceManage;
	}

	/**
	 * 设置解析url请求的编码格式(默认"ISO-8859-1")
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Map<String, String> getStaticHander() {
		return staticHander;
	}

	/**
	 * 添加一个静态资源的映射
	 * @param requestMapping 请求路径
	 * @param staticPesources 响应资源
	 * @return
	 */
	public WebConfig addStaticHander(String requestMapping,String staticPesources) {
		staticHander.put(requestMapping, staticPesources);
		return this;
	}

	public List<String> getHanderPrefixAndSuffix() {
		return handerPrefixAndSuffix;
	}

	/**
	 * 设置一个全局的资源转发/重定向的前后缀
	 * @param prefix 前缀("/WEB-INF/jsp/")
	 * @param suffix 后缀(".jsp")
	 */
	public void setHanderPrefixAndSuffix(String prefix,String suffix) {
		handerPrefixAndSuffix.clear();
		handerPrefixAndSuffix.add(prefix);
		handerPrefixAndSuffix.add(suffix);
	}

	public boolean isPostChangeMethod() {
		return postChangeMethod;
	}

	/**
	 * 是否开启POST请求变换(在POST请求下使用_method改变请求的类型！[_method=get/post/put/delete])
	 * @param postChangeMethod
	 */
	public void postChangeMethod(boolean postChangeMethod) {
		this.postChangeMethod = postChangeMethod;
	}
	
	
	
}
