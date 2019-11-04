package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Component;
import com.lucky.utils.LuckyUtils;

/**
 * 普通IOC组件集合
 * @author DELL
 *
 */
public class ComponentIOC {
	
	
	private Map<String,Object> appMap;
	
	private List<String> appIDS;

	public Map<String, Object> getAppMap() {
		return appMap;
	}

	public void setAppMap(Map<String, Object> appMap) {
		this.appMap = appMap;
	}
	
	public void addAppMap(String id,Object object) {
		if(appMap==null)
			appMap=new HashMap<>();
		appMap.put(id, object);
		addAppIDS(id);
	}

	public List<String> getAppIDS() {
		return appIDS;
	}

	public void setAppIDS(List<String> appIDS) {
		this.appIDS = appIDS;
	}
	
	public void addAppIDS(String id) {
		if(appIDS==null)
			appIDS=new ArrayList<>();
		appIDS.add(id);
	}
	
	/**
	 * 加载Component组件
	 * @param componentClass
	 * @return
	 */
	public ComponentIOC initComponentIOC(List<String> componentClass) {
		for(String clzz:componentClass) {
	 		try {
				Class<?> component=Class.forName(clzz);
				if(component.isAnnotationPresent(Component.class)) {
					Component com=component.getAnnotation(Component.class);
					if(!"".equals(com.id())) {
						addAppMap(com.id(),component.newInstance());
					}else if(!"".equals(com.value())) {
						addAppMap(com.value(),component.newInstance());
					}else {
						addAppMap(LuckyUtils.TableToClass1(component.getSimpleName()),component.newInstance());
					}
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
