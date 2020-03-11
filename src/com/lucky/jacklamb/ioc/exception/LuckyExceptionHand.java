package com.lucky.jacklamb.ioc.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.servlet.Model;

/**
 * 全局异常处理基类
 * 
 * @author fk-7075
 *
 */
public abstract class LuckyExceptionHand {

	/**
	 * 当前请求响应的Controller对象
	 */
	protected Object controllerObj;

	/**
	 * 当前请求响应的Controller对象的Class对象
	 */
	protected Class<?> currClass;

	/**
	 * 当前请求响应的Controller方法
	 */
	protected Method currMethod;
	
	/**
	 * 当前请求响应的Controller方法参数
	 */
	protected Object[] params;

	/**
	 * Model对象
	 */
	protected Model model;

	/**
	 * Controller层面上的指定处理
	 */
	protected List<ExceptionDisposeHand> roleController;

	/**
	 * Method层面上的指定处理
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
	 * Controller全局异常处理
	 * @param e
	 */
	protected void globalExceptionHand(Throwable e) {
		e.printStackTrace();
	}
	
	/**
	 * 指定异常处理
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

