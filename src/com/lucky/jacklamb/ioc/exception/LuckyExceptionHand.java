package com.lucky.jacklamb.ioc.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.lucky.jacklamb.annotation.ioc.Controller;
import com.lucky.jacklamb.servlet.Model;
import com.lucky.jacklamb.utils.LuckyUtils;

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
	 * ExceptionDisposeHand��ע������
	 */
	private List<ExceptionDisposeHand> registry;

	public void initialize(Model model, Object controllerObj, Method currMethod, Object[] params) {
		this.controllerObj = controllerObj;
		this.currClass = controllerObj.getClass();
		this.currMethod = currMethod;
		this.params = params;
		this.model = model;
		registry = new ArrayList<>();
	}

	/**
	 * �ṩ���û���д��Controllerȫ���쳣����
	 * @param e
	 */
	protected void globalExceptionHand(Throwable e) {
		e.printStackTrace();
	}
	
	/**
	 * �ṩ���û���д��ָ���쳣����[ָ���ָ࣬������]
	 */
	public void exceptionHand() {
		
	}
	
	/**
	 * ע��ExceptionDisposeHand
	 * @param exceptionDisposeHands
	 */
	public void registered(ExceptionDisposeHand...exceptionDisposeHands) {
		Stream.of(exceptionDisposeHands).forEach(registry::add);
	}
	

	/**
	 * �ص���������Lucky����
	 * @param e
	 */
	public void exceptionRole(Throwable e) {
		if (registry.isEmpty()) {
			globalExceptionHand(e);
			return;
		}
		String ctrlName = getControllerID();
		String cmethodName = currMethod.getName();
		for(ExceptionDisposeHand methodED:registry) {
			//��������
			if(methodED.root(ctrlName,cmethodName)) {
				methodED.getDispose().dispose(e);
				return;
			}
			//�����
			if(methodED.root(ctrlName)) {
				methodED.getDispose().dispose(e);
				return;
			}

		}
		globalExceptionHand(e);
	}
	
	private String getControllerID() {
		if(currClass.isAnnotationPresent(Controller.class)) {
			Controller annotation = currClass.getAnnotation(Controller.class);
			if(!"".equals(annotation.value()))
				return currClass.getAnnotation(Controller.class).value();
			return LuckyUtils.TableToClass1(currClass.getSimpleName());
		}else {
			return LuckyUtils.TableToClass1(currClass.getSimpleName());
		}
	}
}

