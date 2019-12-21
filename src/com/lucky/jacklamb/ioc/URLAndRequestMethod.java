package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.lucky.jacklamb.enums.RequestMethod;

public class URLAndRequestMethod {
	
	private String url;
	
	private Set<RequestMethod> methods;
	
	public URLAndRequestMethod() {
		methods=new HashSet<>();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Set<RequestMethod> getMethods() {
		return methods;
	}

	public void addMethods(RequestMethod[] methods) {
		Stream.of(methods).forEach(this.methods::add);
	}
	
	public void addMethod(RequestMethod method) {
		this.methods.add(method);
	}

	
	public boolean myEquals(URLAndRequestMethod uRLAndRequestMethod) {
		if(uRLAndRequestMethod==null)
			return false;
		if(!uRLAndRequestMethod.getUrl().equals(url))
			return false;
		for(RequestMethod urmRM:uRLAndRequestMethod.getMethods()) {
			if(methods.contains(urmRM))
				return true;
		}
		return false;
	}
	
	public boolean equalsTemplate(URLAndRequestMethod uRLAndRequestMethod) {
		if(uRLAndRequestMethod==null)
			return false;
		if(!isConform(url,uRLAndRequestMethod.getUrl()))
			return false;
		for(RequestMethod urmRM:uRLAndRequestMethod.getMethods()) {
			if(methods.contains(urmRM))
				return true;
		}
		return false;
	}
	
	/**
	 * 判断当前传入的url是否符合容器中的某一个映射
	 * @param mapstr
	 * @param currurl
	 * @return
	 */
	public boolean isConform(String mapstr,String currurl) {
		String[] mapArray=participle(mapstr);
		String[] urlArray=participle(currurl);
		if(mapArray.length!=urlArray.length)
			return false;
		boolean rest=true;
		for(int i=0;i<mapArray.length;i++)
			rest=rest&&wordVerification(mapArray[i],urlArray[i]);
		return rest;
	}

	/**
	 * 单词汇校验(判断单词是否符合模板)
	 * @param template 模板
	 * @param word 单词
	 * @return
	 */
	public boolean wordVerification(String template,String word) {
		if(template.startsWith("*"))//word必须以template结尾
			return word.endsWith(template.substring(1));
		if(template.endsWith("*"))//word必须以template开始
			return word.startsWith(template.substring(0,template.length()-1));
		if("?".equals(template)||(template.startsWith("#{")&&template.endsWith("}")))//任意word都匹配
			return true;
		return template.equals(word);//没有特殊符号，表示word必须为template
	}
	
	private String[] participle(String url) {
		String[] split = url.split("/");
		List<String> list=new ArrayList<>();
		Stream.of(split).filter(a->!"".equals(a)).forEach(list::add);
		String[] rest=new String[list.size()];
		list.toArray(rest);
		return rest;
	}

	


	public static void main(String[] args) {
		ControllerAndMethodMap map=new ControllerAndMethodMap();
		URLAndRequestMethod urm1=new URLAndRequestMethod();
		urm1.setUrl("aaaa");
		urm1.addMethod(RequestMethod.GET);
		urm1.addMethod(RequestMethod.POST);
		map.put(urm1, new ControllerAndMethod());
		URLAndRequestMethod urm2=new URLAndRequestMethod();
		urm2.setUrl("aaaa");
		urm2.addMethod(RequestMethod.GET);
		
		System.out.println(map.containsKey(urm2));
		
	}
	
	
}
