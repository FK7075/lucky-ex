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
//	 * http����Ľ��뷽ʽ
//	 * @return
//	 */
//	public String getEncoding() {
//		return encoding;
//	}
//
//	/**
//	 * ����http����Ľ��뷽ʽ(ȱʡĬ��Ϊ ISO-8859-1)
//	 * @param encoding
//	 */
//	public void setEncoding(String encoding) {
//		this.encoding = encoding;
//	}
//
//	/**
//	 * ��Դӳ��
//	 * @return
//	 */
//	public Map<String, String> getUrl_paths() {
//		return url_paths;
//	}
//	/**
//	 * ������Դӳ��(/main.do-->/ptoject/WEB-INF/jsp/index.html)
//	 * @param url url����·��
//	 * @param path ӳ�����Դ·��
//	 */
//	public void setUrl_paths(String url,String path) {
//		this.url_paths.put(url, path);
//	}
//	/**
//	 * ȫ�ַ���ǰ׺�ͺ�׺
//	 * @return
//	 */
//	public List<String> getSetterstylenew() {
//		return setterstylenew;
//	}
//	/**
//	 * ����ȫ�ֵķ���ǰ׺("/WEB-INF/jsp/")
//	 * @param prefix
//	 */
//	public void setSetterStylePrefix(String prefix) {
//		this.setterstylenew.set(0, prefix);
//	}
//	/**
//	 * ����ȫ�ֵķ��ʺ�׺(".html")
//	 * @return
//	 */
//	public void setSetterStyleSuffix(String suffix) {
//		this.setterstylenew.set(1, suffix);
//	}
//
//	/**
//	 * ��Ծ���Controller��ǰ��׺����
//	 * @return
//	 */
//	public Map<String, String> getControllerstyle() {
//		return controllerstyle;
//	}
//	
//	/**
//	 * ��Ծ���Controller��ǰ��׺����["mycontroller"="/WEB-INF/jsp/,.jsp"]
//	 * @param controllerId Controller��ID
//	 * @param prefix_suffix prefix,suffix(ǰ��׺��','����)
//	 */
//	public void setControllerstyle(String controllerId,String prefix_suffix) {
//		this.controllerstyle.put(controllerId,prefix_suffix);
//	}
//	
//	/**
//	 * Controller��Դӳ��
//	 * @return
//	 */
//	public List<List<LuckyMapping>> getMapping() {
//		return mapping;
//	}
//	
//	/**
//	 * ����Controller��Դӳ�� 
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
