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
 * url��������urlӳ��ΪControllerAndMethod����,���Ҹ���һЩ��������ת�����ж�������,�Լ���������Ľ��
 * 
 * @author fk-7075
 *
 */
public class UrlParsMap {

	/**
	 * �����������
	 * 
	 * @param request
	 *            Request����
	 * @param response
	 *            Response����
	 * @param come
	 *            ControllerAndMethod����
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
	 * �жϵ�ǰ�����Ƿ����Controller�������޶�����
	 * 
	 * @param method
	 *            ��Ӧ��ǰ������ķ�������
	 * @param reqMet
	 *            ��ǰ���������
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
	 * ����POST�������"_method"��ֵ�ı����������
	 * 
	 * @param request
	 *            Request����
	 * @param method
	 *            ��ǰ����������
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
	 * ��urlӳ��ΪControllerAndMethod����
	 * 
	 * @param beans
	 *            IOC����
	 * @param style
	 *            ǰ��׺����
	 * @param urlMap
	 *            URL-Controller����
	 * @param url
	 *            ȥ����Ŀ����URL��ַ
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
				if( currKey.startsWith("#")) {//��#��ͷ����ʾRest������
					String ck=currKey.substring(1, currKey.length());
					come.restPut(ck, restVs[i]);
				}else if(currKey.startsWith("*")&&!currKey.endsWith("*")) {//��*��ͷ����ʾ��*������ַ���β����
					String ck=currKey.substring(1, currKey.length());
					if(!restVs[i].endsWith(ck))
						return null;
				}else if(!currKey.startsWith("*")&&currKey.endsWith("*")) {//��*��β����ʾ��*ǰ����ַ���ͷ����
					String ck=currKey.substring(0, currKey.length()-1);
					if(!restVs[i].startsWith(ck))
						return null;
				}else if(currKey.startsWith("*")&&currKey.endsWith("*")) {//��*��ͷ��*��β,��ʾ�����м���ַ�����
					String ck=currKey.substring(1, currKey.length()-1);
					if(!restVs[i].contains(ck))
						return null;
				}else if("?".equals(currKey)) {
					//ֻ��?,��ʾƥ������һ���ǿ��ַ�
				}else {//û�������ַ���ʾ����ȫƥ��
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
	 * ���˵�url�еĲ����rest��������
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
	 * Ϊ�����Ķ���ֵ
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
	 * �������������
	 */
	public void closeLuckyWebContext() {
		LuckyWebContext.clearContext();
	}

}
