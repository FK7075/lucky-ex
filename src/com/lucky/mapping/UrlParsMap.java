package com.lucky.mapping;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.annotation.Controller;
import com.lucky.annotation.CrossOrigin;
import com.lucky.annotation.RequestMapping;
import com.lucky.enums.RequestMethod;
import com.lucky.servlet.LuckyWebContext;
import com.lucky.servlet.Model;

/**
 * url解析，将url映射为ControllerAndMethod对象,并且负责一些关于请求转化与判定的事务,以及跨域问题的解决
 * 
 * @author fk-7075
 *
 */
public class UrlParsMap {

	/**
	 * 跨域访问配置
	 * 
	 * @param request
	 *            Request对象
	 * @param response
	 *            Response对象
	 * @param come
	 *            ControllerAndMethod对象
	 */
	public void setCross(HttpServletRequest request, HttpServletResponse response, ControllerAndMethod come) {
		if (come.getController().getClass().isAnnotationPresent(CrossOrigin.class)) {
			CrossOrigin crso = come.getController().getClass().getAnnotation(CrossOrigin.class);
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
	 * 判断当前请求是否符合Controller方法的限定请求
	 * 
	 * @param method
	 *            响应当前的请求的方法对象
	 * @param reqMet
	 *            当前请求的类型
	 * @return
	 */
	public boolean isExistRequestMethod(Method method, RequestMethod reqMet) {
		boolean isEx = false;
		if (method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping mapping = method.getAnnotation(RequestMapping.class);
			RequestMethod[] rms = mapping.method();
			for (RequestMethod me : rms) {
				if (reqMet.equals(me)) {
					isEx = true;
					break;
				}
			}
		}
		return isEx;
	}

	/**
	 * 根据POST请求参数"_method"的值改变请求的类型
	 * 
	 * @param request
	 *            Request对象
	 * @param method
	 *            当前的请求类型
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
	 * 将url映射为ControllerAndMethod对象
	 * 
	 * @param beans
	 *            IOC容器
	 * @param style
	 *            前后缀配置
	 * @param urlMap
	 *            URL-Controller容器
	 * @param url
	 *            去掉项目名的URL地址
	 * @return
	 */
	public ControllerAndMethod pars(Map<String, Object> beans, Map<String, String> style,
			Map<String, ControllerAndMethod> urlMap, String url) {
		ControllerAndMethod come = new ControllerAndMethod();
		String controllerID = "";
		String mapping = getKey(urlMap, url);
		come = urlMap.get(mapping);
		come.setUrl(mapping);
		Method method = come.getMethod();
		RequestMapping rm = method.getAnnotation(RequestMapping.class);
		String rmvalue = rm.value();
		if (rmvalue.contains("->")) {
			String restParamStr = url.replaceAll(mapping + "/", "");
			String[] restVs = restParamStr.split("/");
			int start = rmvalue.indexOf("->");
			rmvalue = rmvalue.substring(start + 2, rmvalue.length());
			String[] restKs = rmvalue.split("/");
			if(restVs.length!=restKs.length)
				return null;
			for (int i = 0; i < restKs.length; i++) {
				String currKey=restKs[i];
				if( currKey.startsWith("#")) {//以#开头，表示Rest参数名
					String ck=currKey.substring(1, currKey.length());
					come.restPut(ck, restVs[i]);
				}else if(currKey.startsWith("*")&&!currKey.endsWith("*")) {//以*开头，表示以*后面的字符结尾即可
					String ck=currKey.substring(1, currKey.length());
					if(!restVs[i].endsWith(ck))
						return null;
				}else if(!currKey.startsWith("*")&&currKey.endsWith("*")) {//以*结尾，表示以*前面的字符开头即可
					String ck=currKey.substring(0, currKey.length()-1);
					if(!restVs[i].startsWith(ck))
						return null;
				}else if(currKey.startsWith("*")&&currKey.endsWith("*")) {//以*开头以*结尾,表示存在中间的字符即可
					String ck=currKey.substring(1, currKey.length()-1);
					if(!restVs[i].contains(ck))
						return null;
				}else if("?".equals(currKey)) {
					//只有?,表示匹配任意一个非空字符
				}else {//没有特殊字符表示参数全匹配
					if(!restVs[i].equals(currKey))
						return null;
				}
			}
		}
		if (come.getController().getClass().isAnnotationPresent(Controller.class)) {
			Controller cot = come.getController().getClass().getAnnotation(Controller.class);
			come.setPrefix(cot.prefix());
			come.setSuffix(cot.suffix());
		} else {
			for (Entry<String, Object> entry : beans.entrySet()) {
				if (entry.getValue().equals(come.getController())) {
					controllerID = entry.getKey();
				}
			}
			String styleStr = style.get(controllerID);
			if (styleStr != null && styleStr.contains(",")) {
				String[] split = styleStr.split(",");
				come.setPrefix(split[0]);
				come.setSuffix(split[1]);
			}

		}
		return come;
	}

	/**
	 * 过滤掉url中的参数项（rest风格参数）
	 * 
	 * @param urlMap
	 * @param url
	 * @return
	 */
	private String getKey(Map<String, ControllerAndMethod> urlMap, String url) {
		if (urlMap.containsKey(url))
			return url;
		if (url.lastIndexOf('/') == 0)
			return null;
		int end = url.lastIndexOf('/');
		url = url.substring(0, end);
		return getKey(urlMap, url);
	}

	/**
	 * 为上下文对象赋值
	 * 
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

}
