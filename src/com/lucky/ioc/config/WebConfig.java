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
	 * ���ý���url����ı����ʽ(Ĭ��"ISO-8859-1")
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Map<String, String> getStaticHander() {
		return staticHander;
	}

	/**
	 * ���һ����̬��Դ��ӳ��
	 * @param requestMapping ����·��
	 * @param staticPesources ��Ӧ��Դ
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
	 * ����һ��ȫ�ֵ���Դת��/�ض����ǰ��׺
	 * @param prefix ǰ׺("/WEB-INF/jsp/")
	 * @param suffix ��׺(".jsp")
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
