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
 * url解析，将url映射为ControllerAndMethod对象,并且负责一些关于请求转化与判定的事务,以及跨域问题的解决
 * 
 * @author fk-7075
 *
 */
public class UrlParsMap {

	/**
	 * 跨域访问配置
	 * @param request Request对象
	 * @param response Response对象
	 * @param come ControllerAndMethod对象
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
	 * 根据POST请求参数"_method"的值改变请求的类型
	 * @param requestRequest对象
	 * @param method当前的请求类型
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
	 * 根据一个请求的URL找到一个与之对应的ControllerAndMethod,如果找不到对应则返回null
	 * @param url 当前请求的URL
	 * @return ControllerAndMethod对象
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
	 * 解析出一个url中的所有Rest参数，封装到一个Map中
	 * @param mapstr 请求映射的模板
	 * @param currurl 符合模板的一个Url
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
	 * 根据当前传入的url地址找到Controller中的映射方法
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

	/**
	 * 为上下文对象封装HttpServletRequest和HttpServletResponse对象
	 * @param model
	 */
	public void setLuckyWebContext(Model model) {
		LuckyWebContext luckyWebContext = LuckyWebContext.createContext();
		luckyWebContext.setRequest(model.getRequest());
		luckyWebContext.setResponse(model.getResponse());
		LuckyWebContext.setContext(luckyWebContext);
	}

	/**
	 * 清除上下文内容
	 */
	public void closeLuckyWebContext() {
		LuckyWebContext.clearContext();
	}

	/**
	 * 为Controller注入Model HttpSession ServletRequest ServletResponse属性
	 * @param obj controller对象
	 * @param model controller方法
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
