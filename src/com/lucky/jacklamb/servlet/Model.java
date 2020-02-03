package com.lucky.jacklamb.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.rest.LSON;
import com.lucky.jacklamb.rest.LXML;
import com.lucky.jacklamb.tcconversion.typechange.JavaConversion;
import com.lucky.jacklamb.utils.ArrayCast;

/**
 * mvc�ĺ�����ת��
 * @author fk-7075
 *
 */
public class Model {
	
	private final String HEAD="<?xml version=\"1.0\" encoding=\"utf-8\"?>";

	/**
	 * ���뷽ʽ
	 */
	private String encod;
	
	/**
	 * Request����
	 */
	private HttpServletRequest req;
	
	/**
	 * Response����
	 */
	private HttpServletResponse resp;
	
	/**
	 * url����ķ���
	 */
	private RequestMethod requestMethod;
	
	/**
	 * ҳ���������Map<String,String[]>
	 */
	private Map<String, String[]> parameterMap;
	
	/**
	 * Rest���Ĳ�������Map<String,String>
	 */
	private Map<String,String> restMap;
	
	private PrintWriter out;

	/**
	 *  Model������
	 * @param request Request����
	 * @param response Response����
	 * @param requestMethod url����ķ���
	 * @param encod  ���뷽ʽ
	 * @throws IOException 
	 */
	public Model(HttpServletRequest request,HttpServletResponse response,RequestMethod requestMethod,String encod) throws IOException {
		this.encod=encod;
		req =request;
		resp =response;
		this.requestMethod=requestMethod;
		this.parameterMap=getRequestParameterMap();
		restMap=new HashMap<>();
	}
	
	public Model(HttpServletRequest request,HttpServletResponse response) throws IOException {
		req =request;
		resp =response;
		this.parameterMap=getRequestParameterMap();
		restMap=new HashMap<>();
	}
	
	/**
	 * �õ����е�Rest���Ĳ�������RestParamMap
	 * @return
	 */
	public Map<String, String> getRestMap() {
		return restMap;
	}

	/**
	 * ����RestParamMap
	 * @param restMap
	 */
	protected void setRestMap(Map<String, String> restMap) {
		this.restMap = restMap;
	}


	/**
	 * �õ�����ҳ���������RequestParameterMap
	 * @return parameterMap--><Map<String,String[]>>
	 */
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}



	/**
	 * �õ���ǰ�������������
	 * @return
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * ���õ�ǰ�������������
	 * @param requestMethod
	 */
	protected void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * ���ı���Ϣд��Cookie
	 * @param name "K"
	 * @param value "V"
	 * @param maxAge  ���ݵ������ʱ��
	 */
	public void setCookieContent(String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		resp.addCookie(cookie);
	}

	/**
	 * ����"name"��ȡCookit�е��ı���Ϣ,��ת��Ϊָ���ı����ʽ
	 * @param name NAME
	 * @param encoding ���뷽ʽ
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCookieContent(String name, String encoding) throws UnsupportedEncodingException {
		String info = null;
		Cookie[] cookies = req.getCookies();
		if(cookies==null)
			return null;
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
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCookieContent(String name) throws UnsupportedEncodingException {
		String info = null;
		Cookie[] cookies = req.getCookies();
		if(cookies==null)
			return null;
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
	 * @param name
	 * @param value
	 */
	public void setRequestAttribute(String name, Object value) {
		req.setAttribute(name, value);
	}

	/**
	 * ��request����ȡObject����ֵ
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return req.getAttribute(name);
	}

	/**
	 * �õ�String���͵�ҳ�����
	 * @param name
	 * @return
	 */
	public String getRequestPrarmeter(String name) {
		return req.getParameter(name);
	}


	/**
	 * ��session���д�ֵ
	 * @param name
	 * @param object
	 */
	public void setSessionAttribute(String name, Object object) {
		req.getSession().setAttribute(name, object);
	}

	/**
	 * ��session����ȡֵ
	 * @param name
	 * @return
	 */
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	/**
	 * ʹ��response�����printIn����д������
	 * @param info
	 * @throws IOException
	 */
	public void responsePrintIn(String info) throws IOException{
		if(out==null)
			out=resp.getWriter();
		out.println(info);
	}

	/**
	 * ʹ��response�����printIn����������ģ��д��ΪJSON��ʽ����
	 * @param pojo(���飬����Collection,Map)
	 * @throws IOException
	 */
	public void printJson(Object pojo) throws IOException{
		LSON lson = new LSON(pojo);
		responsePrintIn(lson.getJsonStr());
	}
	
	/**
	 * ʹ��response�����printIn����������ģ��д��ΪXml��ʽ����
	 * @param pojo(���飬����Collection,Map)
	 * @throws IOException
	 */
	public void printXml(Object pojo) throws IOException{
		LXML lson = new LXML(pojo);
		responsePrintIn(HEAD+lson.getXmlStr());
	}
	
	/**
	 * �ر�PrintWriter
	 */
	public void closeWriter() {
		if(out!=null)
			out.close();
	}

	/**
	 * ʹ��response�����Writer����������ģ��д��ΪJSON��ʽ����
	 * @param pojo(���飬����Collection,Map)
	 * @throws IOException
	 */
	public void witerJson(Object pojo) throws IOException{
		LSON lson = new LSON(pojo);
		responseWriter(lson.getJsonStr());
	}
	
	/**
	 * ʹ��response�����Writer����������ģ��д��ΪXML��ʽ����
	 * @param pojo(���飬����Collection,Map)
	 * @throws IOException
	 */
	public void witerXml(Object pojo) throws IOException{
		LXML lson = new LXML(pojo);
		responseWriter(HEAD+lson.getXmlStr());
	}

	/**
	 * ʹ��response�����Writer����д������
	 * @param info
	 * @throws IOException
	 */
	public void responseWriter(String info) throws IOException{
		if(out==null)
			out=resp.getWriter();
		out.write(info);
	}

	/**
	 * ������Ŀ������file�ļ�(��)�ľ���·��
	 * @param file
	 * @return
	 */
	public String getRealPath(String file) {
		return req.getServletContext().getRealPath(file);
	}
	
	/**
	 * ������Ŀ������file�ļ�(��)��File����
	 * @param file
	 * @return
	 */
	public File getRealFile(String file) {
		String path=getRealPath(file);
		if(path!=null) {
			File fileF=new File(path);
			return fileF;
		}
		return null;
	}
	
	/**
	 * ɾ��DocBase�ļ��е�ĳ���ļ�
	 * @param file
	 * @return
	 */
	public boolean delRealFile(String file) {
		if(file==null||file==""||file=="/")
			return false;
		File delFile=getRealFile(file);
		if(delFile!=null&&delFile.exists()) {
			delFile.delete();
			return true;
		}
		return false;
	}

	/**
	 * �õ�request����
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return req;
	}

	/**
	 * �õ�response����
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return resp;
	}

	/**
	 * �õ�session����
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
	 * �ж�parameterMap���Ƿ����ֵΪparamName��Key
	 * @param paramName
	 * @return
	 */
	public boolean parameterMapContainsKey(String paramName) {
		return parameterMap.containsKey(paramName);
	}
	
	public boolean restMapContainsKey(String paramName) {
		return restMap.containsKey(paramName);
	}
	
	/**
	 * ��String���͵�����תΪ�������͵�����String[]->{Integer[],Double[]....}
	 * @param strArr
	 * @param changTypeClass
	 * @return T[]
	 */
	public <T> T[] strArrayChange(String[] strArr,Class<T> changTypeClass) {
		return  ArrayCast.strArrayChange(strArr, changTypeClass);
	}
	
	/**
	 * �õ�parameterMap��key��ӦString[]
	 * @param key ��
	 * @return
	 */
	public String[] getArray(String key) {
		return parameterMap.get(key);
	}
	
	/**
	 * �õ�parameterMap��key��ӦString[]ת�ͺ��T[]
	 * @param key ��
	 * @param clzz Ŀ������T��Class
	 * @return
	 */
	public <T> T[] getArray(String key,Class<T> clzz){
		return (T[]) ArrayCast.strArrayChange(parameterMap.get(key), clzz);
	}
	
	/**
	 * �õ�RestParamMap��key��ӦValue
	 * @param key
	 * @return
	 */
	public String getRestParam(String key) {
		return restMap.get(key);
	}
	
	/**
	 * �õ�RestParamMap��key��Ӧ��Stringת�ͺ��T
	 * @param key ��
	 * @param clzz Ŀ������T��Class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getRestParam(String key,Class<T> clzz) {
		return (T) JavaConversion.strToBasic(restMap.get(key), clzz);
	}
	
	/**
	 * ת��
	 * @param address
	 * @throws ServletException
	 * @throws IOException
	 */
	public void forward(String address) throws ServletException, IOException {
		req.getRequestDispatcher(address).forward(req, resp);
	}
	
	/**
	 * �ض���
	 * @param address
	 * @throws IOException
	 */
	public void redirect(String address) throws IOException {
		resp.sendRedirect(address);
	}

}
