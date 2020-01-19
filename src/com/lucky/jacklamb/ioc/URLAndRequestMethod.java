package com.lucky.jacklamb.ioc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.servlet.Model;
import com.lucky.jacklamb.utils.Jacklabm;

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
	
	public URLAndRequestMethod findUrl(Model model,List<URLAndRequestMethod> urlList) throws IOException {
		boolean isPass=false;
		URLAndRequestMethod tempURLAndRequestMethod = null;
		for(URLAndRequestMethod temp:urlList) {
			if(isConform(temp.getUrl(),url)) {
				isPass=true;
				tempURLAndRequestMethod=temp;
				break;
			}
		}
		if(!isPass) {
			model.responseWriter(Jacklabm.exception("HTTP Status 404 Not Found", "����ȷ��url��"+url, "�Ҳ���������ƥ���ӳ����,��������URL�Ƿ���ȷ��"));
			return null;
		}
		for(RequestMethod currmethod : methods)
			if(!tempURLAndRequestMethod.getMethods().contains(currmethod)) {
				model.responseWriter(Jacklabm.exception("HTTP Status 500 Internal Server Error","���Ϸ�����������"+this.methods,"������������"+this.methods+",��ǰ��������֧�֣�"));
				return null;
			}
		return tempURLAndRequestMethod;
	}
	
	/**
	 * �жϵ�ǰ�����url�Ƿ���������е�ĳһ��ӳ��
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
	 * ���ʻ�У��(�жϵ����Ƿ����ģ��)
	 * @param template ģ��
	 * @param word ����
	 * @return
	 */
	public boolean wordVerification(String template,String word) {
		if(template.startsWith("[")&&template.endsWith("]")) {//[candidate1,candidate2,candidate3]//��ѡ��ƥ�䣬ƥ������һ������
			String[] split = template.substring(1, template.length()-1).trim().split(",");
			return Arrays.asList(split).contains(word);
		}
		if(template.startsWith("![")&&template.endsWith("]")) {//![candidate1,candidate2,candidate3]//��ѡ�ʷ���ƥ�䣬ƥ����������һ��������
			String[] split = template.substring(2, template.length()-1).trim().split(",");
			return !Arrays.asList(split).contains(word);
		}
		if(template.startsWith("*"))//word������template��β
			return word.endsWith(template.substring(1));
		if(template.endsWith("*"))//word������template��ʼ
			return word.startsWith(template.substring(0,template.length()-1));
		if("?".equals(template)||(template.startsWith("#{")&&template.endsWith("}")))//���������word��ƥ��
			return true;
		return template.equals(word);//û��������ţ���ʾword����Ϊtemplate
	}
	
	private String[] participle(String url) {
		String[] split = url.split("/");
		List<String> list=new ArrayList<>();
		Stream.of(split).filter(a->!"".equals(a)).forEach(list::add);
		String[] rest=new String[list.size()];
		list.toArray(rest);
		return rest;
	}

}
