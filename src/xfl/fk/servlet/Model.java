package xfl.fk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xfl.fk.aop.RequestMethod;
import xfl.fk.json.LSON;
import xfl.fk.utils.ArrayCast;
import xfl.fk.utils.LuckyUtils;

public class Model {

	private String encod;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private RequestMethod requestMethod;
	private Map<String, String[]> parameterMap;
	private Map<String,String> restMap;

	public Model(HttpServletRequest request,HttpServletResponse response,RequestMethod requestMethod,String encod) {
		this.encod=encod;
		req =request;
		resp =response;
		this.requestMethod=requestMethod;
		this.parameterMap=getRequestParameterMap();
		restMap=new HashMap<>();
	}
	
	/**
	 * 得到RestParamMap
	 * @return
	 */
	public Map<String, String> getRestMap() {
		return restMap;
	}

	/**
	 * 设置RestParamMap
	 * @param restMap
	 */
	public void setRestMap(Map<String, String> restMap) {
		this.restMap = restMap;
	}


	/**
	 * 得到RequestParameterMap
	 * @return parameterMap--><Map<String,String[]>>
	 */
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}



	/**
	 * 得到当前请求的请求类型
	 * 
	 * @return
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置当前请求的请求类型
	 * 
	 * @param requestMethod
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * 将文本信息写入Cookie
	 * 
	 * @param name
	 *            "K"
	 * @param value
	 *            "V"
	 * @param maxAge
	 *            内容的最长保存时间
	 */
	public void setCookieContent(String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		resp.addCookie(cookie);
	}

	/**
	 * 根据"name"获取Cookit中的文本信息,并转化为指定的编码格式
	 * 
	 * @param name
	 *            NAME
	 * @param encoding
	 *            编码方式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCookieContent(String name, String encoding) throws UnsupportedEncodingException {
		String info = null;
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				info = cookie.getValue();
				info = URLDecoder.decode(info, encoding);
			}
		}
		return info;
	}

	/**
	 * 根据"name"获取Cookit中的文本信息(UTF-8)
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCookieContent(String name) throws UnsupportedEncodingException {
		String info = null;
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				info = cookie.getValue();
				info = URLDecoder.decode(info, "UTF-8");
			}
		}
		return info;
	}

	/**
	 * 向request域对象中存值
	 * 
	 * @param name
	 * @param value
	 */
	public void setRequestAttribute(String name, Object value) {
		req.setAttribute(name, value);
	}

	/**
	 * 向request域中取Object类型值
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return req.getAttribute(name);
	}

	/**
	 * 向request域中取String类型值
	 * @param name
	 * @return
	 */
	public String getRequestPrarmeter(String name) {
		return req.getParameter(name);
	}

	/**
	 * 设置转发或重定向的目的地 1.转发到页面：无前缀 2.转发到Controller方法:forward:method
	 * 3.重定向到页面：page:pageing 4.重定向到Controller方法：redirect:method
	 * 
	 * @param view
	 */

	/**
	 * 向session域中存值
	 * 
	 * @param name
	 * @param object
	 */
	public void setSessionAttribute(String name, Object object) {
		req.getSession().setAttribute(name, object);
	}

	/**
	 * 向session域中取值
	 * 
	 * @param name
	 * @return
	 */
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	/**
	 * 使用response对象的printIn方法写出数据
	 * 
	 * @param info
	 * @throws IOException
	 */
	public void responsePrintIn(String info) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println(info);
		out.flush();
		out.close();
	}

	/**
	 * 使用response对象的printIn方法将对象模型写出为JSON格式数据
	 * 
	 * @param pojo
	 *            对象模型(数组，对象，List)
	 * @throws IOException
	 */
	public void printJson(Object pojo) throws IOException {
		LSON lson = new LSON(pojo);
		responsePrintIn(lson.getJsonStr());
	}

	/**
	 * 使用response对象的Writer方法将对象模型写出为JSON格式数据
	 * 
	 * @param pojo(数组，对象，List)
	 * @throws IOException
	 */
	public void witerJson(Object pojo) throws IOException {
		LSON lson = new LSON(pojo);
		responseWriter(lson.getJsonStr());
	}

	/**
	 * 使用response对象的Writer方法写出数据
	 * 
	 * @param info
	 * @throws IOException
	 */
	public void responseWriter(String info) throws IOException {
		PrintWriter out = resp.getWriter();
		out.write(info);
		out.flush();
		out.close();
	}

	/**
	 * 返回项目发布后file文件(夹)的绝对路径
	 * 
	 * @param file
	 * @return
	 */
	public String getRealPath(String file) {
		return req.getServletContext().getRealPath(file);
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return req;
	}

	/**
	 * 得到response对象
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return resp;
	}

	/**
	 * 得到session对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return req.getSession();
	}

	/**
	 * 得到RequestParameterMap
	 * @return parameterMap--><Map<String,String[]>>
	 */
	private Map<String, String[]> getRequestParameterMap(){
		HttpServletRequest request = getRequest();
		Map<String, String[]>res=new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] mapStr = entry.getValue();
			String[] mapStr_cpoy =new String[mapStr.length];
			for (int i = 0; i < mapStr.length; i++) {
				try {
					String characterEncoding = request.getCharacterEncoding();
					mapStr_cpoy[i] = new String(mapStr[i].getBytes(encod), characterEncoding);
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			res.put(entry.getKey(), mapStr_cpoy);
		}
		return res;
	}
	
	/**
	 * 得到parameterMap中String[]转型后的T[]
	 * @param key 键
	 * @param clzz 目标类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] getArray(String key,Class<T> clzz){
		return (T[]) ArrayCast.strToList(parameterMap.get(key), clzz.getSimpleName());
	}
	
	/**
	 * 得到RestParamMap中String转型后的T
	 * @param key
	 * @param clzz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRestParam(String key,Class<T> clzz) {
		return (T) LuckyUtils.typeCast(restMap.get(key), clzz.getSimpleName());
	}

}
