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
	
	
	private WebConfig() {
		encoding="ISO-8859-1";
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
	
}
