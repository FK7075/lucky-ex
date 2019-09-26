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
	 * �õ�RestParamMap
	 * @return
	 */
	public Map<String, String> getRestMap() {
		return restMap;
	}

	/**
	 * ����RestParamMap
	 * @param restMap
	 */
	public void setRestMap(Map<String, String> restMap) {
		this.restMap = restMap;
	}


	/**
	 * �õ�RequestParameterMap
	 * @return parameterMap--><Map<String,String[]>>
	 */
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}



	/**
	 * �õ���ǰ�������������
	 * 
	 * @return
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * ���õ�ǰ�������������
	 * 
	 * @param requestMethod
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * ���ı���Ϣд��Cookie
	 * 
	 * @param name
	 *            "K"
	 * @param value
	 *            "V"
	 * @param maxAge
	 *            ���ݵ������ʱ��
	 */
	public void setCookieContent(String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		resp.addCookie(cookie);
	}

	/**
	 * ����"name"��ȡCookit�е��ı���Ϣ,��ת��Ϊָ���ı����ʽ
	 * 
	 * @param name
	 *            NAME
	 * @param encoding
	 *            ���뷽ʽ
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
	 * ����"name"��ȡCookit�е��ı���Ϣ(UTF-8)
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
	 * ��request������д�ֵ
	 * 
	 * @param name
	 * @param value
	 */
	public void setRequestAttribute(String name, Object value) {
		req.setAttribute(name, value);
	}

	/**
	 * ��request����ȡObject����ֵ
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return req.getAttribute(name);
	}

	/**
	 * ��request����ȡString����ֵ
	 * @param name
	 * @return
	 */
	public String getRequestPrarmeter(String name) {
		return req.getParameter(name);
	}

	/**
	 * ����ת�����ض����Ŀ�ĵ� 1.ת����ҳ�棺��ǰ׺ 2.ת����Controller����:forward:method
	 * 3.�ض���ҳ�棺page:pageing 4.�ض���Controller������redirect:method
	 * 
	 * @param view
	 */

	/**
	 * ��session���д�ֵ
	 * 
	 * @param name
	 * @param object
	 */
	public void setSessionAttribute(String name, Object object) {
		req.getSession().setAttribute(name, object);
	}

	/**
	 * ��session����ȡֵ
	 * 
	 * @param name
	 * @return
	 */
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	/**
	 * ʹ��response�����printIn����д������
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
	 * ʹ��response�����printIn����������ģ��д��ΪJSON��ʽ����
	 * 
	 * @param pojo
	 *            ����ģ��(���飬����List)
	 * @throws IOException
	 */
	public void printJson(Object pojo) throws IOException {
		LSON lson = new LSON(pojo);
		responsePrintIn(lson.getJsonStr());
	}

	/**
	 * ʹ��response�����Writer����������ģ��д��ΪJSON��ʽ����
	 * 
	 * @param pojo(���飬����List)
	 * @throws IOException
	 */
	public void witerJson(Object pojo) throws IOException {
		LSON lson = new LSON(pojo);
		responseWriter(lson.getJsonStr());
	}

	/**
	 * ʹ��response�����Writer����д������
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
	 * ������Ŀ������file�ļ�(��)�ľ���·��
	 * 
	 * @param file
	 * @return
	 */
	public String getRealPath(String file) {
		return req.getServletContext().getRealPath(file);
	}

	/**
	 * �õ�request����
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return req;
	}

	/**
	 * �õ�response����
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return resp;
	}

	/**
	 * �õ�session����
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return req.getSession();
	}

	/**
	 * �õ�RequestParameterMap
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
	 * �õ�parameterMap��String[]ת�ͺ��T[]
	 * @param key ��
	 * @param clzz Ŀ������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] getArray(String key,Class<T> clzz){
		return (T[]) ArrayCast.strToList(parameterMap.get(key), clzz.getSimpleName());
	}
	
	/**
	 * �õ�RestParamMap��Stringת�ͺ��T
	 * @param key
	 * @param clzz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRestParam(String key,Class<T> clzz) {
		return (T) LuckyUtils.typeCast(restMap.get(key), clzz.getSimpleName());
	}

}
