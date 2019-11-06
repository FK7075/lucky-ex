package com.lucky.ioc.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.ioc.PackageScan;

public class WebConfig {
	
	private static WebConfig webConfig;
	
	private String encoding;
	
	private Map<String,String> staticHander;
	
	private List<String> handerPrefixAndSuffix;
	
	
	private WebConfig() {
		encoding="ISO-8859-1";
		handerPrefixAndSuffix=new ArrayList<>();;
		handerPrefixAndSuffix.add("");handerPrefixAndSuffix.add("");
		staticHander=new HashMap<>();
	}

	public String getEncoding() {
		return encoding;
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
		handerPrefixAndSuffix.add(prefix);handerPrefixAndSuffix.add(suffix);
	}
	
	public static WebConfig getWebConfig() {
		if(webConfig==null)
			webConfig=new WebConfig();
		PackageScan ps = PackageScan.getPackageScan();
		List<String> cfgsuffix = new ArrayList<>();
		cfgsuffix.add("appconfig");
		List<String> cfgClass = ps.loadComponent(cfgsuffix);
		for (String clzz : cfgClass) {
			try {
				Class<?> cfg = Class.forName(clzz);
				if (ApplicationConfig.class.isAssignableFrom(cfg)) {
					ApplicationConfig cfConfig = (ApplicationConfig) cfg.newInstance();
					cfConfig.webConfig(webConfig);
					break;
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				continue;
			} catch (InstantiationException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
		}
		return webConfig;
	}
	
	
	
	
	
}
