package com.lucky.jacklamb.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;

import com.lucky.jacklamb.enums.Rest;
import com.lucky.jacklamb.ioc.ControllerAndMethod;

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
	private void toPage(Model model,String info,List<String> pre_suf) throws IOException, ServletException {
		String topage="";
		if(info.contains("page:")) {//�ض���ҳ��
			info=info.replaceAll("page:", "");
			topage=model.getRequest().getContextPath()+pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			model.redirect(topage);
		}else if(info.contains("forward:")) {//ת������Controller��ĳ������
			info=info.replaceAll("forward:", "");
			model.forward(info);
		}else if(info.contains("redirect:")) {//�ض��򵽱�Controller��ĳ������
			info=info.replaceAll("redirect:", "");
			model.redirect(info);
		}else {//ת����ҳ��
			topage=pre_suf.get(0)+info+pre_suf.get(1);
			topage=topage.replaceAll(" ", "");
			model.forward(topage);
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
	public void jump(Model model,ControllerAndMethod controllerAndMethod, Method method, Object obj)
			throws IOException, ServletException {
		if (obj != null) {
			if(controllerAndMethod.getRest()==Rest.JSON) {
				model.witerJson(obj);
				return;
			}
			if(controllerAndMethod.getRest()==Rest.XML) {
				model.witerXml(obj);
				return;
			}
			if(controllerAndMethod.getRest()==Rest.TXT) {
				model.writer(obj.toString());
				return;
			}
			if(controllerAndMethod.getRest()==Rest.NO) {
				if(String.class.isAssignableFrom(obj.getClass())) {
					toPage(model,obj.toString(),controllerAndMethod.getPreAndSuf());
				}else {
					throw new RuntimeException("����ֵ���ʹ����޷����ת�����ض������!�Ϸ��ķ���ֵ����ΪString������λ�ã�"+controllerAndMethod.getMethod());
				}
			}
				
		}
	}
}
