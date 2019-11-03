package com.lucky.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lucky.ioc.ControllerAndMethod;
import com.lucky.servlet.Model;
import com.lucky.xml.LuckyXmlConfig;

public class ApplicationBeans {
	
	private HanderMapping hander;
	private static ApplicationBeans applicationBeans;
	private Map<String, Object> beans;
	private Map<String, ControllerAndMethod> mapping;
	private Map<String,String> pre_suf;
	private List<String> setter_pre_suf;
	private String encod;
	
	
	
	public String getEncod() {
		return encod;
	}

	public void setEncod(String encod) {
		this.encod = encod;
	}

	public List<String> getSetter_pre_suf() {
		return setter_pre_suf;
	}

	public Map<String, String> getPre_suf() {
		return pre_suf;
	}

	public Map<String, ControllerAndMethod> getMapping() {
		return mapping;
	}

	public Map<String, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<String, Object> beans) {
		hander.setBeans(beans);
		this.beans = beans;
	}

	public void setMapping(Map<String, ControllerAndMethod> mapping) {
		this.mapping = mapping;
	}

	public Object getMapping(String name) {
		return this.mapping.get(name);
	}
	
	public Object getBean(String name) {
		return this.beans.get(name);
	}
	
	private  ApplicationBeans() {
		hander=new HanderMapping();
		mapping=new HashMap<>();
		hander.doInstance();
		hander.doScanToXml();
		hander.doMapper();
		hander.doAutowired();
		this.mapping.putAll(hander.UrlHanding());
		this.mapping.putAll(hander.UrlHandingXml());
		this.pre_suf=hander.getPre_suf();
		this.setter_pre_suf=hander.getSetter_pre_suf();
		this.encod=hander.getEncod();
		try {
			hander.findExpandMethod();
			hander.pourProxyObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		hander.doProxy();
		if (hander.getAopMap() != null) {
			hander.doNowAutowired();
		}
		
		this.beans=hander.getBeans();
	}
	public static ApplicationBeans getApplicationBeans() {
		if(applicationBeans==null) {
			return new ApplicationBeans();
		}else {
			return applicationBeans;
		}
	}

	public Set<String> getControllerMaps() {
		return hander.getControllerMaps();
	}



	public void findExpandMethod() {
		hander.findExpandMethod();
		
	}


	public void pourProxyObject() throws ClassNotFoundException {
		hander.pourProxyObject();
		this.beans=hander.getBeans();
	}

	public void doProxy() {
		hander.doProxy();
		
	}

	public Object getAopMap() {
		return hander.getAopMap();
	}



	public void doNowAutowired() {
		hander.doNowAutowired();
		this.beans=hander.getBeans();
	}



	public void autowReqAdnResp(Object obj,Model model) throws IllegalArgumentException, IllegalAccessException {
		hander.autowReqAdnResp(obj,model);
	}



	public Map<String,Object> getHanderMaps2() {
		return hander.getHanderMaps2();
	}
	
	public void doMapper() {
		hander.doMapper();
		this.beans=hander.getBeans();
	}
	
	public Map<String,String> getUrlAdnPath(){
		LuckyXmlConfig xmlCfg = LuckyXmlConfig.loadLuckyXmlConfig();
		return xmlCfg.getMvcxml().getUrl_paths();
	}

}
