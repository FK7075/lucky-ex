//package com.lucky.xml;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MvcXmlModel {
//	
//	private Map<String,String> url_paths;
//	private List<String> setterstylenew;
//	private Map<String,String> controllerstyle;
//	private List<List<LuckyMapping>> mapping;
//	private String encoding="ISO-8859-1";
//	
//	public MvcXmlModel() {
//		url_paths=new HashMap<>();
//		setterstylenew=new ArrayList<>();
//		setterstylenew.add("");
//		setterstylenew.add("");
//		controllerstyle=new HashMap<>();
//		mapping=new ArrayList<>();
//	}
//	
//	/**
//	 * http请求的解码方式
//	 * @return
//	 */
//	public String getEncoding() {
//		return encoding;
//	}
//
//	/**
//	 * 设置http请求的解码方式(缺省默认为 ISO-8859-1)
//	 * @param encoding
//	 */
//	public void setEncoding(String encoding) {
//		this.encoding = encoding;
//	}
//
//	/**
//	 * 资源映射
//	 * @return
//	 */
//	public Map<String, String> getUrl_paths() {
//		return url_paths;
//	}
//	/**
//	 * 设置资源映射(/main.do-->/ptoject/WEB-INF/jsp/index.html)
//	 * @param url url请求路径
//	 * @param path 映射的资源路径
//	 */
//	public void setUrl_paths(String url,String path) {
//		this.url_paths.put(url, path);
//	}
//	/**
//	 * 全局访问前缀和后缀
//	 * @return
//	 */
//	public List<String> getSetterstylenew() {
//		return setterstylenew;
//	}
//	/**
//	 * 设置全局的访问前缀("/WEB-INF/jsp/")
//	 * @param prefix
//	 */
//	public void setSetterStylePrefix(String prefix) {
//		this.setterstylenew.set(0, prefix);
//	}
//	/**
//	 * 设置全局的访问后缀(".html")
//	 * @return
//	 */
//	public void setSetterStyleSuffix(String suffix) {
//		this.setterstylenew.set(1, suffix);
//	}
//
//	/**
//	 * 针对具体Controller的前后缀配置
//	 * @return
//	 */
//	public Map<String, String> getControllerstyle() {
//		return controllerstyle;
//	}
//	
//	/**
//	 * 针对具体Controller的前后缀配置["mycontroller"="/WEB-INF/jsp/,.jsp"]
//	 * @param controllerId Controller的ID
//	 * @param prefix_suffix prefix,suffix(前后缀以','隔开)
//	 */
//	public void setControllerstyle(String controllerId,String prefix_suffix) {
//		this.controllerstyle.put(controllerId,prefix_suffix);
//	}
//	
//	/**
//	 * Controller资源映射
//	 * @return
//	 */
//	public List<List<LuckyMapping>> getMapping() {
//		return mapping;
//	}
//	
//	/**
//	 * 设置Controller资源映射 
//	 * @param mapping
//	 */
//	public void setMapping(List<List<LuckyMapping>> mapping) {
//		this.mapping = mapping;
//	}
//	@Override
//	public String toString() {
//		return "MvcXmlModel [url_paths=" + url_paths + ", setterstylenew=" + setterstylenew + ", controllerstyle="
//				+ controllerstyle + ", mapping=" + mapping + "]";
//	}
//
//
//}
