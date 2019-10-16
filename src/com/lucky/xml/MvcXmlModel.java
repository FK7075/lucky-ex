package com.lucky.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MvcXmlModel {
	
	private Map<String,String> url_paths;
	private List<String> setterstylenew;
	private Map<String,String> controllerstyle;
	private List<List<LuckyMapping>> mapping;
	private String encoding="ISO-8859-1";
	
	public MvcXmlModel() {
		url_paths=new HashMap<>();
		setterstylenew=new ArrayList<>();
		setterstylenew.add("");
		setterstylenew.add("");
		controllerstyle=new HashMap<>();
		mapping=new ArrayList<>();
	}
	
	
	
	public String getEncoding() {
		return encoding;
	}



	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}



	public Map<String, String> getUrl_paths() {
		return url_paths;
	}
	public void setUrl_paths(String url,String path) {
		this.url_paths.put(url, path);
	}
	public List<String> getSetterstylenew() {
		return setterstylenew;
	}
	public void setSetterStylePrefix(String prefix) {
		this.setterstylenew.set(0, prefix);
	}
	public void setSetterStyleSuffix(String suffix) {
		this.setterstylenew.set(1, suffix);
	}
	public Map<String, String> getControllerstyle() {
		return controllerstyle;
	}
	public void setControllerstyle(String key,String value) {
		this.controllerstyle.put(key, value);
	}
	public List<List<LuckyMapping>> getMapping() {
		return mapping;
	}
	public void setMapping(List<List<LuckyMapping>> mapping) {
		this.mapping = mapping;
	}
	@Override
	public String toString() {
		return "MvcXmlModel [url_paths=" + url_paths + ", setterstylenew=" + setterstylenew + ", controllerstyle="
				+ controllerstyle + ", mapping=" + mapping + "]";
	}


}
