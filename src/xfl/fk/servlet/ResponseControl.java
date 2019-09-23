package xfl.fk.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xfl.fk.utils.LuckyUtils;

/**
 * ������Ӧ��ص���
 * @author fk-7075
 *
 */
public class ResponseControl {
	
	
	/**
	 * ��Ӧ��ǰ����
	 * @param req Request����
	 * @param resp Response����
	 * @param info ��Ӧ��Ŀ��
	 * @param pre_suf ǰ��׺����
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toPage(HttpServletRequest req,HttpServletResponse resp,String info,List<String> pre_suf) throws IOException, ServletException {
		String topage="";
		if(info.contains("page:")) {//�ض���ҳ��
			info=info.replaceAll("page:", "");
			topage=req.getContextPath()+pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			resp.sendRedirect(topage);
		}else if(info.contains("forward:")) {//ת������Controller��ĳ������
			info=info.replaceAll("forward:", "");
			req.getRequestDispatcher(info).forward(req, resp);
		}else if(info.contains("redirect:")) {//�ض��򵽱�Controller��ĳ������
			info=info.replaceAll("redirect:", "");
			resp.sendRedirect(info);
		}else {//ת����ҳ��
			topage=pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			req.getRequestDispatcher(topage).forward(req, resp);
		}
	}

	/**
	 * ������Ӧ��Ϣ
	 * @param req Request����
	 * @param resp Response����
	 * @param pre_suf ǰ��׺����
	 * @param method ��Ӧ����ķ���
	 * @param obj ִ����Ӧ�����Ķ���
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
