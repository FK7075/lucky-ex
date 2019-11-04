package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Service;
import com.lucky.utils.LuckyUtils;

public class ServiceIOC {

	
	private Map<String,Object> serviceMap;
	
	private List<String> serviceIDS;

	public Map<String, Object> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, Object> serviceMap) {
		this.serviceMap = serviceMap;
	}
	
	public void addServiceMap(String id,Object object) {
		if(serviceMap==null)
			serviceMap=new HashMap<>();
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
		if(serviceIDS==null)
			serviceIDS=new ArrayList<>();
		serviceIDS.add(id);
	}
	
	/**
	 * 加载Service组件到ServiceIOC容器
	 * @param serviceClass
	 * @return
	 */
	public ServiceIOC initServiceIOC(List<String> serviceClass) {
		for(String clzz:serviceClass) {
			try {
				Class<?> service=Class.forName(clzz);
				if(service.isAnnotationPresent(Service.class)) {
					Service ser=service.getAnnotation(Service.class);
					if(!"".equals(ser.value()))
						addServiceMap(ser.value(), service.newInstance());
					else
						addServiceMap(LuckyUtils.TableToClass1(service.getSimpleName()), service.newInstance());
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
