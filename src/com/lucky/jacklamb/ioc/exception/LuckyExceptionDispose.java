package com.lucky.jacklamb.ioc.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lucky.jacklamb.annotation.mvc.ExceptionHander;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.ioc.ApplicationBeans;

public class LuckyExceptionDispose extends LuckyExceptionHand {
	
	private static Logger log=Logger.getLogger(LuckyExceptionDispose.class);
	
	private List<Object> beans;
	
	public LuckyExceptionDispose() {
		try {
			beans = ApplicationBeans.createApplicationBeans().getBeans(ExceptionDispose.class);
		}catch(NotFindBeanException e) {
			beans=new ArrayList<>();
			log.debug("�û�û��ע��ExceptionDispose�����Controller�в����쳣��Ҫ��Controller�н��д��������Ҫ�����쳣������ʹ��@ExceptionHanderע�ⶨ��һ��ExceptionDispose");
		}
	}

	/**
	 * ȫ���쳣����
	 */
	@Override
	protected void globalExceptionHand(Throwable e) {
		ExceptionDispose exceobj;
		ExceptionHander eh;
		for(Object obj:beans) {
			exceobj=(ExceptionDispose) obj;
			eh=exceobj.getClass().getAnnotation(ExceptionHander.class);
			if(eh.value().length==0) {
				exceobj.dispose(e);
				return;
			}
		}
		e.printStackTrace();
	}


	/**
	 * ָ���쳣����ע��
	 */
	@Override
	public void exceptionHand() {
		ExceptionDispose exceobj;
		ExceptionDisposeHand edh;
		ExceptionHander eh;
		String[] scope;
		for(Object obj:beans) {
			exceobj=(ExceptionDispose) obj;
			eh=exceobj.getClass().getAnnotation(ExceptionHander.class);
			scope=eh.value();
			exceobj.init(model,controllerObj, currClass, currMethod, params);
			edh=new ExceptionDisposeHand(scope,exceobj);
			registered(edh);
		}
	}
	
	

}
