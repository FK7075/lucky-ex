package com.lucky.ioc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Controller;
import com.lucky.annotation.RequestMapping;
import com.lucky.exception.NotAddIOCComponent;
import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

public class ControllerIOC {

	private Map<String, Object> controllerMap;

	private List<String> controllerIDS;
	
	private Map<String, ControllerAndMethod> handerMap;
	
	private Map<String,String> mapping;
	
	
	

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	public Map<String, ControllerAndMethod> getHanderMap() {
		return handerMap;
	}

	public void setHanderMap(Map<String, ControllerAndMethod> handerMap) {
		this.handerMap = handerMap;
	}

	public ControllerIOC() {
		controllerMap = new HashMap<>();
		controllerIDS = new ArrayList<>();
		handerMap = new HashMap<>();
		mapping = new HashMap<>();
	}
	
	public boolean containHander(String id) {
		return handerMap.containsKey(id);
	}
	
	public ControllerAndMethod getControllerAndMethod(String id) {
		if(!containHander(id))
			throw new NotFindBeanException("在ControllerAndMethod(ioc)容器中找不到ID为--" + id + "--的Bean...");
		return handerMap.get(id);
	}

	public boolean containId(String id) {
		return controllerIDS.contains(id);
	}

	public Object getControllerBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("在Controller(ioc)容器中找不到ID为--" + id + "--的Bean...");
		return controllerMap.get(id);
	}

	public Map<String, Object> getControllerMap() {
		return controllerMap;
	}

	public void setControllerMap(Map<String, Object> controllerMap) {
		this.controllerMap = controllerMap;
	}

	public void addControllerMap(String id, Object object) {
		if (containId(id))
			throw new NotAddIOCComponent("Controller(ioc)容器中已存在ID为--" + id + "--的组件，无法重复添加......");
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
		controllerIDS.add(id);
	}

	/**
	 * 加载Controller组件
	 * 
	 * @param controllerClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public ControllerIOC initControllerIOC(List<String> controllerClass)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		for (String clzz : controllerClass) {
			Class<?> controller = Class.forName(clzz);
			if (controller.isAnnotationPresent(Controller.class)) {
				Controller cont = controller.getAnnotation(Controller.class);
				if (!"".equals(cont.value()))
					addControllerMap(cont.value(), controller.newInstance());
				else
					addControllerMap(LuckyUtils.TableToClass1(controller.getSimpleName()), controller.newInstance());
			}

		}
		return this;
	}

	/**
	 * URL-ControllerMethod的映射解析
	 * 
	 * @return
	 */
	public void methodHanderSetting() {
		for (Map.Entry<String, Object> entry : controllerMap.entrySet()) {
			Object instance = entry.getValue();
			Class<?> clzz = instance.getClass();
			String url_c;
			if (clzz.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping crm = clzz.getAnnotation(RequestMapping.class);
				url_c = crm.value();
				if (!"/".equals(url_c)) {
					if (!url_c.startsWith("/"))
						url_c = "/" + url_c;
					if (!url_c.endsWith("/"))
						url_c += "/";
				}
			} else {
				url_c = "/";
			}
			Method[] publicMethods = clzz.getDeclaredMethods();
			for (Method method : publicMethods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					ControllerAndMethod come = new ControllerAndMethod();
					come.setController(entry.getValue());
					RequestMapping mrm = method.getAnnotation(RequestMapping.class);
					String url_m;
					if (mrm.value().contains("->")) {
						int end = mrm.value().indexOf("->");
						url_m = mrm.value().substring(0, end);
						if (url_m.startsWith("/"))
							url_m = url_m.substring(1, url_m.length());
					} else {
						url_m = mrm.value();
						if (url_m.startsWith("/"))
							url_m = url_m.substring(1, url_m.length());
					}
					come.setMethod(method);
					handerMap.put(url_c + url_m, come);
					mapping.put(url_c + url_m, clzz.getName()+"."+method.getName()+"(x,x,x)");
				} else {
					continue;
				}
			}
		}
	}

}
