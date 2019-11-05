package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Component;
import com.lucky.exception.CreateBeanException;
import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

/**
 * 普通IOC组件集合
 * @author DELL
 *
 */
public class ComponentIOC {
	
	
	private Map<String,Object> appMap;
	
	private List<String> appIDS;
	
	
	public boolean containId(String id) {
		if(appIDS==null)
			return false;
		return appIDS.contains(id);
	}
	
	public Object getComponentBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("在Component(ioc)容器中找不到ID为--"+id+"--的Bean...");
		return appMap.get(id);
		
	}

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
			Class<?> component = null;
	 		try {
				component=Class.forName(clzz);
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
				throw new CreateBeanException("没有发现"+component.getName()+"的无参构造器，无法创建对象...");
			}
		}
		return this;
	}
	
}
