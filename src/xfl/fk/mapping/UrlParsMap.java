package xfl.fk.mapping;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xfl.fk.annotation.Controller;
import xfl.fk.annotation.CrossOrigin;
import xfl.fk.annotation.RequestMapping;
import xfl.fk.aop.RequestMethod;

/**
 * url解析，将url映射为ControllerAndMethod对象,并且负责一些关于请求转化与判定的事务,以及跨域问题的解决
 * @author fk-7075
 *
 */
public class UrlParsMap {
	
	
	/**
	 * 配置跨域访问配置
	 * @param response Response对象
	 * @param come ControllerAndMethod对象
	 */
	public void setCross(HttpServletRequest request,HttpServletResponse response,ControllerAndMethod come) {
		if(come.getController().getClass().isAnnotationPresent(CrossOrigin.class)||come.getMethod().isAnnotationPresent(CrossOrigin.class)) {
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("XDomainRequestAllowed","1"); 
		}
	}
	
	/**
	 * 判断当前请求是否符合Controller方法的限定请求
	 * @param method 响应当前的请求的方法对象
	 * @param reqMet 当前请求的类型
	 * @return
	 */
	public boolean isExistRequestMethod(Method method,RequestMethod reqMet) {
		boolean isEx=false;
		if(method.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping mapping=method.getAnnotation(RequestMapping.class);
			RequestMethod[] rms=mapping.method();
			for(RequestMethod me:rms) {
				if(reqMet.equals(me)) {
					isEx=true;
					break;
				}
			}
		}
		return isEx;
	}
	
	/**
	 * 根据POST请求参数"_method"的值改变请求的类型
	 * @param request Request对象
	 * @param method 当前的请求类型
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public RequestMethod chagenMethod(HttpServletRequest request, HttpServletResponse response,RequestMethod method) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		if(method==RequestMethod.POST) {
			String hihMeth=request.getParameter("_method");
			if(hihMeth!=null) {
				hihMeth=hihMeth.toUpperCase();
				if("POST".equals(hihMeth))
					return RequestMethod.POST;
				else if("GET".equals(hihMeth))
					return RequestMethod.GET;
				else if("PUT".equals(hihMeth))
					return RequestMethod.PUT;
				else if("DELETE".equals(hihMeth))
					return RequestMethod.DELETE;
			}else {
				return method;
			}
		}
		
		return method;
	}
	

	/**
	 * 将url映射为ControllerAndMethod对象
	 * @param beans IOC容器
	 * @param style 前后缀配置
	 * @param urlMap URL-Controller容器
	 * @param url 去掉项目名的URL地址
	 * @return
	 */
	public ControllerAndMethod pars(Map<String,Object> beans,Map<String,String> style,Map<String,ControllerAndMethod> urlMap,String url) {
		ControllerAndMethod come=new ControllerAndMethod();
		String controllerID="";
		String mapping=getKey(urlMap,url);
		come=urlMap.get(mapping);
		come.setUrl(mapping);
		Method method=come.getMethod();
		RequestMapping rm=method.getAnnotation(RequestMapping.class);
		String rmvalue=rm.value();
		if(rmvalue.contains("->"));{
			String restParamStr=url.replaceAll(mapping+"/", "");
			String[] restVs = restParamStr.split("/");
			int start=rmvalue.indexOf("->");
			rmvalue=rmvalue.substring(start+2,rmvalue.length());
			String[] restKs=rmvalue.split(",");
			for(int i=0;i<restKs.length;i++)
				come.restPut(restKs[i], restVs[i]);
		}
		if(come.getController().getClass().isAnnotationPresent(Controller.class)) {
			Controller cot=come.getController().getClass().getAnnotation(Controller.class);
			come.setPrefix(cot.prefix());
			come.setSuffix(cot.suffix());
		}else {
			for(Entry<String, Object> entry:beans.entrySet()) {
				if(entry.getValue().equals(come.getController())) {
					controllerID=entry.getKey();
				}
			}
			String styleStr=style.get(controllerID);
			if(styleStr!=null&&styleStr.contains(",")) {
				String[] split = styleStr.split(",");
				come.setPrefix(split[0]);
				come.setSuffix(split[1]);
			}
			
		}
		return come;
	}
	
	/**
	 * 过滤掉url中的参数项（rest风格参数）
	 * @param urlMap
	 * @param url
	 * @return
	 */
	private String getKey(Map<String,ControllerAndMethod> urlMap,String url) {
		if(urlMap.containsKey(url))
			return url;
		if(url.lastIndexOf('/')==0)
			return null;
		int end=url.lastIndexOf('/');
		url=url.substring(0,end);
		return getKey(urlMap,url);
	}
	
	
}
