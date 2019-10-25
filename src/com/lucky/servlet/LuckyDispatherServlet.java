package com.lucky.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.annotation.Download;
import com.lucky.annotation.RequestMapping;
import com.lucky.enums.RequestMethod;
import com.lucky.mapping.AnnotationOperation;
import com.lucky.mapping.ApplicationBeans;
import com.lucky.mapping.ControllerAndMethod;
import com.lucky.mapping.UrlParsMap;
import com.lucky.utils.LuckyUtils;

@MultipartConfig
public class LuckyDispatherServlet extends HttpServlet {
	
	private RequestMethod method=RequestMethod.POST;
	private static final long serialVersionUID = 3808567874497317419L;
	private AnnotationOperation anop = new AnnotationOperation();
	private Map<String, Object> beans=new HashMap<>();
	private Map<String, ControllerAndMethod> handerMaps;
	private Map<String,String> url_path;
	private ApplicationBeans app;
	private UrlParsMap urlParsMap;
	private ResponseControl responseControl;

	public void init(ServletConfig config) {
		LuckyUtils.welcome();
		urlParsMap=new UrlParsMap();
		responseControl=new ResponseControl();
		app=ApplicationBeans.getApplicationBeans();
		beans=app.getBeans();
		url_path=app.getUrlAdnPath();
		this.handerMaps=app.getMapping();
		System.out.println("JackLabm:)-> Lucky自动创建的对象:"+app.getBeans());
		System.out.println("JackLabm:)-> Controller:"+app.getControllerMaps());
		System.out.println("JackLabm:)-> Lucky已知的url映射关系"+app.getHanderMaps2());
		System.out.println("JackLabm:)-> Lucky对象注入成功。");
	}

	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.DELETE;
		this.doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.PUT;
		this.doPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.GET;
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.method=urlParsMap.chagenMethod(req,resp,this.method);
			String uri = req.getRequestURI();
			uri=java.net.URLDecoder.decode(new String(uri.getBytes(app.getEncod()), req.getCharacterEncoding()), req.getCharacterEncoding());
			Model model=new Model(req,resp,this.method,app.getEncod());
			urlParsMap.setLuckyWebContext(model);
			try {
				app.findExpandMethod();
				app.pourProxyObject();
				app.setBeans(beans);//还原
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			app.doProxy();
			if (app.getAopMap() != null) {
				app.doNowAutowired();
			}
			String context = req.getContextPath();
			String path = uri.replace(context, "");
			if (path.endsWith(".do")||path.endsWith(".xfl")||path.endsWith("fk")||path.endsWith(".cad")||path.endsWith(".lcl")) {
				path = path.substring(0, path.lastIndexOf("."));
			}
			ControllerAndMethod controllerAndMethod = urlParsMap.pars(beans,app.getPre_suf(),handerMaps, path);
			if(controllerAndMethod==null) {
				resp.getWriter().print("<h3>找不与请求相匹配的映射资源,请检查您的URL是否正确[404:"+req.getRequestURL()+"]....</h3>");
				return;
			}
			model.setRestMap(controllerAndMethod.getRestKV());
			urlParsMap.setCross(req,resp, controllerAndMethod);
			String murl = controllerAndMethod.getUrl();
			if(url_path.containsKey(murl)) {
				List<String> pre_suf=app.getSetter_pre_suf();
				String forwardurl="";
				if(!pre_suf.isEmpty()&&pre_suf.size()==2) {
					forwardurl=pre_suf.get(0)+url_path.get(murl)+pre_suf.get(1);
				}else {
					forwardurl=url_path.get(murl);
				}
				req.getRequestDispatcher(forwardurl).forward(req, resp);
			}else {
				Method method = controllerAndMethod.getMethod();
				if(method==null) {
					resp.getWriter().print("<h3>找不与请求相匹配的映射资源,请检查您的URL是否正确[404:"+req.getRequestURL()+"]....</h3>");
					return;
				}
				if(!urlParsMap.isExistRequestMethod(method,this.method)&&method.isAnnotationPresent(RequestMapping.class)) {
					resp.getWriter().print("<h3>500:您的请求类型为"+this.method+",当前方法并不支！</h3>");
				}else {
					boolean isDownload = method.isAnnotationPresent(Download.class);
					List<String> pre_suf;
					Object obj = controllerAndMethod.getController();
					List<String> setterPreSuf=app.getSetter_pre_suf();
					if(!setterPreSuf.isEmpty()) {
						pre_suf=setterPreSuf;
					}
					pre_suf=controllerAndMethod.getPreAndSuf();
					app.autowReqAdnResp(obj,model);
					Object[] args;
					Object obj1 = new Object();
					args = (Object[]) anop.getControllerMethodParam(model,method);
					obj1 = method.invoke(obj, args);
					if (isDownload == true)//下载操作
						anop.download(model, method);
					responseControl.jump(model,pre_suf, method, obj1);
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			this.method=RequestMethod.POST;
			urlParsMap.closeLuckyWebContext();
		}
	}
}
