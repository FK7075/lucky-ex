package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Controller;
import com.lucky.utils.LuckyUtils;

public class ControllerIOC {
	
	private Map<String,Object> controllerMap;
	
	private List<String> controllerIDS;
	
	public boolean containId(String id) {
		if(controllerIDS==null)
			return false;
		return controllerIDS.contains(id);
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
	 * ¼ÓÔØController×é¼þ
	 * @param controllerClass
	 * @return
	 */
	public ControllerIOC initControllerIOC(List<String> controllerClass) {
		for(String clzz:controllerClass) {
			try {
				Class<?> controller=Class.forName(clzz);
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
				continue;
			}
			
		}
		return this;
	}

}
