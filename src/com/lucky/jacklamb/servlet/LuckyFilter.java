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
import javax.servlet.http.HttpSession;

public abstract class LuckyFilter implements Filter {

	protected FilterConfig filterConfig;

	protected Model model;

	protected FilterChain filterChain;

	protected String uri;

	protected HttpServletRequest request;

	protected HttpServletResponse response;
	
	protected HttpSession session;

	@Override
	public void destroy() {
	}

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}


	/**
	 * ԭʼ�Ĺ������߼�
	 */
	@Override
	public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		request = (HttpServletRequest) req;
		response = (HttpServletResponse) resp;
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		uri = request.getRequestURI();
		model = new Model(request, response);
		session=model.getSession();
		filterChain = chain;
		filter();
	}
	
	/**
	 * �����߼�(�û�ʵ��)
	 * @throws IOException
	 * @throws ServletException
	 */
	public abstract void filter() throws IOException, ServletException;


	/**
	 * ����
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void pass() throws IOException, ServletException {
		filterChain.doFilter(request, response);
	}

	/**
	 * ���в��������������󽫻ᱻת����ָ��Url
	 * @param passUrls ����ͨ��������
	 * @param forwardUrl ת��Url
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void passForward(String[] passUrls, String forwardUrl) throws IOException, ServletException {
		boolean isPass = true;
		String type;
		for (String url : passUrls) {
			if (uri.equals(url)) {
				pass();
				return;
			}
			if (StaticResourceManage.isStaticResource(response,url)) {
				type = requestLastStr(url);
				type = type.substring(type.indexOf("."));
				isPass = isPass && !uri.endsWith(type);
			}
		}
		if (StaticResourceManage.isStaticResource(response, uri)&&isPass) {
			StaticResourceManage.response(request, response, uri);
			return;
		} else {
			model.forward(forwardUrl);
		}
	}
	
	/**
	 * ͨ�����������������󽫻ᱻ�ض���ָ��Url
	 * @param passUrls ����ͨ��������
	 * @param redirectUrl �ض���Url
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void passRedirect(String[] passUrls, String redirectUrl) throws IOException, ServletException {
		boolean isPass = true;
		String type;
		for (String url : passUrls) {
			if (uri.equals(url)) {
				pass();
				return;
			}
			if (StaticResourceManage.isStaticResource(response,url)) {
				type = requestLastStr(url);
				type = type.substring(type.indexOf("."));
				isPass = isPass && !uri.endsWith(type);
			}
		}
		if (StaticResourceManage.isStaticResource(response, uri)&&isPass) {
			StaticResourceManage.response(request, response, uri);
			return;
		} else {
			model.redirect(redirectUrl);
		}
	}
	

	private String requestLastStr(String url) {
		return url.substring(url.lastIndexOf("/"));
	}

}
