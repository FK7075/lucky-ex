package com.lucky.jacklamb.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.ioc.Service;
import com.lucky.jacklamb.aop.util.PointRunFactory;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

public class ServiceIOC extends ComponentFactory{

	private Map<String, Object> serviceMap;

	private List<String> serviceIDS;
	
	public ServiceIOC() {
		serviceMap=new HashMap<>();
		serviceIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return serviceIDS.contains(id);
	}

	public Object getServiceBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("在Service(ioc)容器中找不到ID为--" + id + "--的Bean...");
		return serviceMap.get(id);
	}

	public Map<String, Object> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, Object> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public void addServiceMap(String id, Object object) {
		if(containId(id))
			throw new NotAddIOCComponent("Service(ioc)容器中已存在ID为--"+id+"--的组件，无法重复添加（您可能配置了同名的@Service组件，这将会导致异常的发生！）......");
		serviceMap.put(id, object);
		addServiceIDS(id);
	}

	public List<String> getServiceIDS() {
		return serviceIDS;
	}

	public void setServiceIDS(List<String> serviceIDS) {
		this.serviceIDS = serviceIDS;
	}

	public void addServiceIDS(String id) {
		serviceIDS.add(id);
	}

	/**
	 * 加载Service组件到ServiceIOC容器
	 * 
	 * @param serviceClass
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public ServiceIOC initServiceIOC(List<String> serviceClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		String beanID;
		for (String clzz : serviceClass) {
			Class<?> service = Class.forName(clzz);
			if (service.isAnnotationPresent(Service.class)) {
				Service ser = service.getAnnotation(Service.class);
				if (!"".equals(ser.value()))
					beanID=ser.value();
				else
					beanID=LuckyUtils.TableToClass1(service.getSimpleName());
				addServiceMap(beanID, PointRunFactory.agent(AgentIOC.getAgentIOC().getAgentMap(), "service", beanID, service));
			}
		}
		return this;
	}
}
