package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class WebConfig {
	
	private static WebConfig webConfig;
	
	/**
	 * ������������ʽ
	 */
	private String encoding;
	
	/**
	 * ��̬��Դӳ������
	 */
	private Map<String,String> staticHander;
	
	/**
	 * ȫ�ֵ���Ӧǰ��׺����
	 */
	private List<String> handerPrefixAndSuffix;
	
	/**
	 * �Ƿ���Lucky�ľ�̬��Դ������
	 */
	private boolean openStaticResourceManage;
	
	/**
	 * �Ƿ���post�������������ת��
	 */
	private boolean postChangeMethod;
	
	/**
	 * ȫ����Դ��IP����
	 */
	private List<String> globalResourcesIpRestrict;
	
	/**
	 * ��̬��Դ��IP����
	 */
	private List<String> staticResourcesIpRestrict;
	
	/**
	 * ָ����Դ��IP����
	 */
	private Map<String,List<String>> specifiResourcesIpRestrict;
	
	
	private WebConfig() {
		encoding="ISO-8859-1";
		openStaticResourceManage=false;
		postChangeMethod=false;
		handerPrefixAndSuffix=new ArrayList<>();;
		handerPrefixAndSuffix.add("");handerPrefixAndSuffix.add("");
		staticHander=new HashMap<>();
		globalResourcesIpRestrict=new ArrayList<>();
		staticResourcesIpRestrict=new ArrayList<>();
		specifiResourcesIpRestrict=new HashMap<>();
		
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
	
	public List<String> getGlobalResourcesIpRestrict() {
		return globalResourcesIpRestrict;
	}

	public void addGlobalResourcesIpRestrict(String... ips) {
		Stream.of(ips).forEach(globalResourcesIpRestrict::add);
	}

	public List<String> getStaticResourcesIpRestrict() {
		return staticResourcesIpRestrict;
	}

	public void addStaticResourcesIpRestrict(String... ips) {
		Stream.of(ips).forEach(staticResourcesIpRestrict::add);
	}

	public Map<String, List<String>> getSpecifiResourcesIpRestrict() {
		return specifiResourcesIpRestrict;
	}

	public void setSpecifiResourcesIpRestrict(Map<String, List<String>> specifiResourcesIpRestrict) {
		this.specifiResourcesIpRestrict = specifiResourcesIpRestrict;
	}

	/**
	 * �Ƿ�����̬�ļ�������(Ĭ�Ϲر� false)
	 * @param openStaticResourceManage
	 */
	public void openStaticResourceManage(boolean openStaticResourceManage) {
		this.openStaticResourceManage = openStaticResourceManage;
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
		handerPrefixAndSuffix.add(prefix);
		handerPrefixAndSuffix.add(suffix);
	}

	public boolean isPostChangeMethod() {
		return postChangeMethod;
	}

	/**
	 * �Ƿ���POST����任(��POST������ʹ��_method�ı���������ͣ�[_method=get/post/put/delete])
	 * @param postChangeMethod
	 */
	public void postChangeMethod(boolean postChangeMethod) {
		this.postChangeMethod = postChangeMethod;
	}
	
	
	
}
