package com.lucky.jacklamb.ioc.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.servlet.Model;

/**
 * ȫ���쳣�������
 * 
 * @author fk-7075
 *
 */
public abstract class LuckyExceptionHand {

	/**
	 * ��ǰ������Ӧ��Controller����
	 */
	protected Object controllerObj;

	/**
	 * ��ǰ������Ӧ��Controller�����Class����
	 */
	protected Class<?> currClass;

	/**
	 * ��ǰ������Ӧ��Controller����
	 */
	protected Method currMethod;
	
	/**
	 * ��ǰ������Ӧ��Controller��������
	 */
	protected Object[] params;

	/**
	 * Model����
	 */
	protected Model model;

	/**
	 * Controller�����ϵ�ָ������
	 */
	protected List<ExceptionDisposeHand> roleController;

	/**
	 * Method�����ϵ�ָ������
	 */
	protected List<ExceptionDisposeHand> roleMethod;

	public void initialize(Model model, Object controllerObj, Method currMethod, Object[] params) {
		this.controllerObj = controllerObj;
		this.currClass = controllerObj.getClass();
		this.currMethod = currMethod;
		this.params = params;
		this.model = model;
		roleController = new ArrayList<>();
		roleMethod = new ArrayList<>();
	}

	/**
	 * Controllerȫ���쳣����
	 * @param e
	 */
	protected void globalExceptionHand(Throwable e) {
		e.printStackTrace();
	}
	
	/**
	 * ָ���쳣����
	 */
	public void exceptionHand() {
		
	}

	public void exceptionRole(Throwable e) {
		if (roleController.isEmpty() && roleMethod.isEmpty()) {
			globalExceptionHand(e);
			return;
		}
		String ctrlName = currClass.getSimpleName();
		String cmethodName = currMethod.getName();
		for(ExceptionDisposeHand methodED:roleMethod) {
			if(methodED.root(ctrlName+"."+cmethodName)) {
				methodED.getDispose().dispose(model, e);
				return;
			}
		}
		
		for(ExceptionDisposeHand controllerED:roleController) {
			if(controllerED.root(ctrlName)) {
				controllerED.getDispose().dispose(model, e);
				return;
			}
		}
		globalExceptionHand(e);
	}
}

