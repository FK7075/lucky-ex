package com.lucky.jacklamb.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.WebConfig;

public abstract class LuckyFilter implements Filter {
	
	protected FilterConfig filterConfig;
	
	protected Model model;
	
	protected FilterChain filterChain;
	
	protected WebConfig webConfig;
	
	protected String uri;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException{
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		String encoding=webConfig.getEncoding();
		uri = request.getRequestURI();
		uri=java.net.URLDecoder.decode(new String(uri.getBytes(encoding), req.getCharacterEncoding()), req.getCharacterEncoding());
		if(webConfig.isOpenStaticResourceManage()) {
			//静态资源处理
			StaticResourceManage.response(request, response, uri);
			return;
		}
		model=new Model(request,response);
		filterChain=chain;
		isRelease();
	}
	
	public abstract void isRelease()throws IOException, ServletException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		filterConfig=arg0;
		webConfig=Configuration.getConfiguration().getWebConfig();
	}
	
	protected void release() throws IOException, ServletException {
		filterChain.doFilter(model.getRequest(), model.getResponse());
	}

}
