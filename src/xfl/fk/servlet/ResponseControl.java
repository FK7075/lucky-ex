package xfl.fk.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xfl.fk.utils.LuckyUtils;

/**
 * 处理响应相关的类
 * @author fk-7075
 *
 */
public class ResponseControl {
	
	
	/**
	 * 响应当前请求
	 * @param req Request对象
	 * @param resp Response对象
	 * @param info 响应的目标
	 * @param pre_suf 前后缀配置
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toPage(HttpServletRequest req,HttpServletResponse resp,String info,List<String> pre_suf) throws IOException, ServletException {
		String topage="";
		if(info.contains("page:")) {//重定向到页面
			info=info.replaceAll("page:", "");
			topage=req.getContextPath()+pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			resp.sendRedirect(topage);
		}else if(info.contains("forward:")) {//转发到本Controller的某个方法
			info=info.replaceAll("forward:", "");
			req.getRequestDispatcher(info).forward(req, resp);
		}else if(info.contains("redirect:")) {//重定向到本Controller的某个方法
			info=info.replaceAll("redirect:", "");
			resp.sendRedirect(info);
		}else {//转发到页面
			topage=pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			req.getRequestDispatcher(topage).forward(req, resp);
		}
	}

	/**
	 * 处理响应信息
	 * @param req Request对象
	 * @param resp Response对象
	 * @param pre_suf 前后缀配置
	 * @param method 响应请求的方法
	 * @param obj 执行响应方法的对象
	 * @throws IOException
	 * @throws ServletException
	 */
	public void jump(HttpServletRequest req, HttpServletResponse resp,List<String> pre_suf, Method method, Object obj)
			throws IOException, ServletException {
		if (obj != null) {
			String res="";
			if (LuckyUtils.isJavaClass(obj.getClass())) {
				res=obj.toString();
				toPage(req,resp,res,pre_suf);
			} 
		}
	}


}
