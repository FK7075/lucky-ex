package com.lucky.jacklamb.mapping;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.jacklamb.annotation.ioc.Controller;
import com.lucky.jacklamb.annotation.mvc.CrossOrigin;
import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.ControllerAndMethod;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.servlet.LuckyWebContext;
import com.lucky.jacklamb.servlet.Model;

/**
 * url��������urlӳ��ΪControllerAndMethod����,���Ҹ���һЩ��������ת�����ж�������,�Լ���������Ľ��
 * 
 * @author fk-7075
 *
 */
public class UrlParsMap {

	/**
	 * �����������
	 * @param request Request����
	 * @param response Response����
	 * @param come ControllerAndMethod����
	 */
	public void setCross(HttpServletRequest request, HttpServletResponse response, ControllerAndMethod come) {
		if (come.getController().getClass().isAnnotationPresent(CrossOrigin.class)) {
			CrossOrigin crso = come.getController().getClass().getAnnotation(CrossOrigin.class);
			String url = request.getHeader("Origin");
			String[] url_v = crso.value();
			String[] url_o = crso.origins();
			if ((url_v.length != 0 && url_o.length != 0)
					&& (!Arrays.asList(url_v).contains(url) && !Arrays.asList(url_o).contains(url)))
				url = "fk-xfl-wl";
			String isCookie = "false";
			if (crso.allowCredentials())
				isCookie = "true";
			response.setHeader("Access-Control-Allow-Origin", url);
			response.setHeader("Access-Control-Allow-Methods", crso.method());
			response.setHeader("Access-Control-Max-Age", crso.maxAge() + "");
			response.setHeader("Access-Control-Allow-Headers", crso.allowedHeaders());
			response.setHeader("Access-Control-Allow-Credentials", isCookie);
			response.setHeader("XDomainRequestAllowed", "1");
		}
		if (come.getMethod().isAnnotationPresent(CrossOrigin.class)) {
			CrossOrigin crso = come.getMethod().getAnnotation(CrossOrigin.class);
			String url = request.getHeader("Origin");
			String[] url_v = crso.value();
			String[] url_o = crso.origins();
			if ((url_v.length != 0 && url_o.length != 0)
					&& (!Arrays.asList(url_v).contains(url) && !Arrays.asList(url_o).contains(url)))
				url = "fk-xfl-cl";
			String isCookie = "false";
			if (crso.allowCredentials())
				isCookie = "true";
			response.setHeader("Access-Control-Allow-Origin", url);
			response.setHeader("Access-Control-Allow-Methods", crso.method());
			response.setHeader("Access-Control-Max-Age", crso.maxAge() + "");
			response.setHeader("Access-Control-Allow-Headers", crso.allowedHeaders());
			response.setHeader("Access-Control-Allow-Credentials", isCookie);
			response.setHeader("XDomainRequestAllowed", "1");
		}
	}

	/**
	 * ����POST�������"_method"��ֵ�ı����������
	 * @param requestRequest����
	 * @param method��ǰ����������
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public RequestMethod chagenMethod(HttpServletRequest request, HttpServletResponse response, RequestMethod method)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		if (method == RequestMethod.POST) {
			String hihMeth = request.getParameter("_method");
			if (hihMeth != null) {
				hihMeth = hihMeth.toUpperCase();
				if ("POST".equals(hihMeth))
					return RequestMethod.POST;
				else if ("GET".equals(hihMeth))
					return RequestMethod.GET;
				else if ("PUT".equals(hihMeth))
					return RequestMethod.PUT;
				else if ("DELETE".equals(hihMeth))
					return RequestMethod.DELETE;
			} else {
				return method;
			}
		}
		return method;
	}
	
	/**
	 * ����һ�������URL�ҵ�һ����֮��Ӧ��ControllerAndMethod,����Ҳ�����Ӧ�򷵻�null
	 * @param url ��ǰ�����URL
	 * @return ControllerAndMethod����
	 */
	public ControllerAndMethod pars(String url) {
		ControllerAndMethod come = new ControllerAndMethod();
		String mapping=getKey(ApplicationBeans.createApplicationBeans().getHanderMethods().keySet(),url);
		if(mapping==null)
			return null;
		come = ApplicationBeans.createApplicationBeans().getHanderMethods().get(mapping);
		come.setUrl(mapping);
		come.setRestKV(getRestKV(mapping, url));
		Controller cont=come.getController().getClass().getAnnotation(Controller.class);
		List<String> globalprefixAndSuffix=Configuration.getConfiguration().getWebConfig().getHanderPrefixAndSuffix();
 		come.setPrefix(globalprefixAndSuffix.get(0));
		come.setSuffix(globalprefixAndSuffix.get(1));
		if(!"".equals(cont.prefix()))
			come.setPrefix(cont.prefix());
		if(!"".equals(cont.suffix()))
			come.setSuffix(cont.suffix());
		return come;
	}

	/**
	 * ������һ��url�е�����Rest��������װ��һ��Map��
	 * @param mapstr ����ӳ���ģ��
	 * @param currurl ����ģ���һ��Url
	 * @return
	 */
	public Map<String,String> getRestKV(String mapstr,String currurl){
		Map<String,String> restKV=new HashMap<>();
		String[] mapArray=participle(mapstr);
		String[] urlArray=participle(currurl);
		for(int i=0;i<mapArray.length;i++) {
			if(mapArray[i].startsWith("#{")&&mapArray[i].endsWith("}")) {
				restKV.put(mapArray[i].substring(2,mapArray[i].length()-1).trim(), urlArray[i]);
			}
		}
		return restKV;
	}
	
	/**
	 * ���ݵ�ǰ�����url��ַ�ҵ�Controller�е�ӳ�䷽��
	 * @param keySet
	 * @param currurl
	 * @return
	 */
	public String getKey(Set<String> keySet,String currurl) {
		for(String key:keySet) {
			if(isConform(key,currurl))
				return key;
		}
		return null;
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
		if(template.startsWith("*"))//word������template��β
			return word.endsWith(template.substring(1));
		if(template.endsWith("*"))//word������template��ʼ
			return word.startsWith(template.substring(0,template.length()-1));
		if("?".equals(template)||(template.startsWith("#{")&&template.endsWith("}")))//����word��ƥ��
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

	/**
	 * Ϊ�����Ķ����װHttpServletRequest��HttpServletResponse����
	 * @param model
	 */
	public void setLuckyWebContext(Model model) {
		LuckyWebContext luckyWebContext = LuckyWebContext.createContext();
		luckyWebContext.setRequest(model.getRequest());
		luckyWebContext.setResponse(model.getResponse());
		LuckyWebContext.setContext(luckyWebContext);
	}

	/**
	 * �������������
	 */
	public void closeLuckyWebContext() {
		LuckyWebContext.clearContext();
	}

	/**
	 * ΪControllerע��Model HttpSession ServletRequest ServletResponse����
	 * @param obj controller����
	 * @param model controller����
	 */
	public void autowReqAdnResp(Object obj, Model model) {
		try {
			ApplicationBeans.iocContainers.autowReqAdnResp(obj, model);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
