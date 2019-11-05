package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Controller;
import com.lucky.exception.CreateBeanException;
import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

public class ControllerIOC {
	
	private Map<String,Object> controllerMap;
	
	private List<String> controllerIDS;
	
	public boolean containId(String id) {
		if(controllerIDS==null)
			return false;
		return controllerIDS.contains(id);
	}
	
	public Object getControllerBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("在Controller(ioc)容器中找不到ID为--"+id+"--的Bean...");
		return controllerMap.get(id);
	}
	
	public Map<String, Object> getControllerMap() {
		return controllerMap;
	}

	public void setControllerMap(Map<String, Object> controllerMap) {
		this.controllerMap = controllerMap;
	}
	
	public void addControllerMap(String id,Object object) {
		if(controllerMap==null)
			controllerMap=new HashMap<>();
		controllerMap.put(id, object);
		addControllerIDS(id);
	}


	public List<String> getControllerIDS() {
		return controllerIDS;
	}

	public void setControllerIDS(List<String> controllerIDS) {
		this.controllerIDS = controllerIDS;
	}
	
	public void addControllerIDS(String id) {
		if(controllerIDS==null)
			controllerIDS=new ArrayList<>();
		controllerIDS.add(id);
	}
	
	/**
	 * 加载Controller组件
	 * @param controllerClass
	 * @return
	 */
	public ControllerIOC initControllerIOC(List<String> controllerClass) {
		for(String clzz:controllerClass) {
			Class<?> controller = null;
			try {
				controller=Class.forName(clzz);
				if(controller.isAnnotationPresent(Controller.class)) {
					Controller cont=controller.getAnnotation(Controller.class);
					if(!"".equals(cont.value()))
						addControllerMap(cont.value(), controller.newInstance());
					else
						addControllerMap(LuckyUtils.TableToClass1(controller.getSimpleName()), controller.newInstance());
				}
			} catch (ClassNotFoundException e) {
				continue;
			} catch (InstantiationException e) {
				continue;
			} catch (IllegalAccessException e) {
				throw new CreateBeanException("没有发现"+controller.getName()+"的无参构造器，无法创建对象...");
			}
			
		}
		return this;
	}
	

}
