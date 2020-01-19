package com.lucky.jacklamb.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.jacklamb.annotation.mvc.Download;
import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.ControllerAndMethod;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.WebConfig;
import com.lucky.jacklamb.mapping.AnnotationOperation;
import com.lucky.jacklamb.mapping.UrlParsMap;
import com.lucky.jacklamb.utils.Jacklabm;

@MultipartConfig
public class LuckyDispatherServlet extends HttpServlet {
	
	private RequestMethod method=RequestMethod.POST;
	private static final long serialVersionUID = 3808567874497317419L;
	private AnnotationOperation anop;
	private WebConfig webCfg;
	private UrlParsMap urlParsMap;
	private ResponseControl responseControl;
	

	public void init(ServletConfig config) {
		ApplicationBeans.createApplicationBeans();
		anop = new AnnotationOperation();
		webCfg=Configuration.getConfiguration().getWebConfig();
		urlParsMap=new UrlParsMap();
		responseControl=new ResponseControl();
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
			String encoding=webCfg.getEncoding();
			this.method=urlParsMap.chagenMethod(req,resp,this.method,webCfg.isPostChangeMethod());
			String uri = req.getRequestURI();
			uri=java.net.URLDecoder.decode(new String(uri.getBytes(encoding), req.getCharacterEncoding()), req.getCharacterEncoding());
			Model model=new Model(req,resp,this.method,encoding);
			urlParsMap.setLuckyWebContext(model);
			String context = req.getContextPath();
			String path = uri.replace(context, "");
			if(webCfg.isOpenStaticResourceManage()&&StaticResourceManage.isStaticResource(resp,path)) {
				//静态资源处理
				StaticResourceManage.response(req, resp, uri);
				return;
			}
			if (path.endsWith(".do")||path.endsWith(".xfl")||path.endsWith(".fk")||path.endsWith(".cad")||path.endsWith(".lcl")) {
				//Lucky默认可以使用的后缀
				path = path.substring(0, path.lastIndexOf("."));
			}
			if(webCfg.getStaticHander().containsKey(path)) {
				//扫描并执行配置中的映射
				String forwardurl=webCfg.getHanderPrefixAndSuffix().get(0)+webCfg.getStaticHander().get(path)+webCfg.getHanderPrefixAndSuffix().get(1);
				req.getRequestDispatcher(forwardurl).forward(req, resp);
			}else {
				ControllerAndMethod controllerAndMethod = urlParsMap.pars(model,path,this.method);
				if(controllerAndMethod==null)
					return;
				if(!controllerAndMethod.ipExistsInRange(req.getRemoteAddr())||!controllerAndMethod.ipISCorrect(req.getRemoteAddr())) {
					resp.getWriter().print(Jacklabm.exception("HTTP Status 403 Blocking Access","不合法的请求ip："+req.getRemoteAddr(),"该ip地址没有被注册，服务器拒绝响应！"));
					return;
				}
				else {
					model.setRestMap(controllerAndMethod.getRestKV());
					urlParsMap.setCross(req,resp, controllerAndMethod);
					Method method = controllerAndMethod.getMethod();
					boolean isDownload = method.isAnnotationPresent(Download.class);
					Object obj = controllerAndMethod.getController();
					urlParsMap.autowReqAdnResp(obj,model);
					Object[] args;
					Object obj1 = new Object();
					args = (Object[]) anop.getControllerMethodParam(model,method);
					obj1 = method.invoke(obj, args);
					if (isDownload == true)//下载操作
						anop.download(model, method);
					responseControl.jump(model,controllerAndMethod, method, obj1);
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
