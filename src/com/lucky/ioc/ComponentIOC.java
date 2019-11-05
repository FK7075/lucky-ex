package com.lucky.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Bean;
import com.lucky.annotation.BeanFactory;
import com.lucky.annotation.Component;
import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

/**
 * 普通IOC组件集合
 * 
 * @author DELL
 *
 */
public class ComponentIOC {

	private Map<String, Object> appMap;

	private List<String> appIDS;
	
	public ComponentIOC() {
		appMap=new HashMap<>();
		appIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return appIDS.contains(id);
	}

	public Object getComponentBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("在Component(ioc)容器中找不到ID为--" + id + "--的Bean...");
		return appMap.get(id);

	}

	public Map<String, Object> getAppMap() {
		return appMap;
	}

	public void setAppMap(Map<String, Object> appMap) {
		this.appMap = appMap;
	}

	public void addAppMap(String id, Object object) {
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
		appIDS.add(id);
	}

	/**
	 * 加载Component组件
	 * 
	 * @param componentClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public ComponentIOC initComponentIOC(List<String> componentClass)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (String clzz : componentClass) {
			Class<?> component = Class.forName(clzz);
			if (component.isAnnotationPresent(Component.class)) {
				Component com = component.getAnnotation(Component.class);
				if (!"".equals(com.id())) {
					addAppMap(com.id(), component.newInstance());
				} else if (!"".equals(com.value())) {
					addAppMap(com.value(), component.newInstance());
				} else {
					addAppMap(LuckyUtils.TableToClass1(component.getSimpleName()), component.newInstance());
				}
			} else if (component.isAnnotationPresent(BeanFactory.class)) {
				Object obj = component.newInstance();
				Method[] methods=component.getDeclaredMethods();
				for(Method met:methods) {
					if(met.isAnnotationPresent(Bean.class)) {
						Object invoke = met.invoke(obj);
						Bean bean=met.getAnnotation(Bean.class);
						if("".equals(bean.value())) {
							addAppMap(LuckyUtils.TableToClass1(met.getReturnType().getSimpleName()), invoke);
						}else {
							addAppMap(bean.value(),invoke);
						}
						
					}
				}
			}
		}
		return this;
	}

}
