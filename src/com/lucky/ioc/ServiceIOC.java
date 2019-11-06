package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.annotation.Service;
import com.lucky.exception.NotAddIOCComponent;
import com.lucky.exception.NotFindBeanException;
import com.lucky.utils.LuckyUtils;

public class ServiceIOC {

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
			throw new NotFindBeanException("��Service(ioc)�������Ҳ���IDΪ--" + id + "--��Bean...");
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
			throw new NotAddIOCComponent("Service(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ����......");
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
	 * ����Service�����ServiceIOC����
	 * 
	 * @param serviceClass
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ServiceIOC initServiceIOC(List<String> serviceClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		for (String clzz : serviceClass) {
			Class<?> service = Class.forName(clzz);
			if (service.isAnnotationPresent(Service.class)) {
				Service ser = service.getAnnotation(Service.class);
				if (!"".equals(ser.value()))
					addServiceMap(ser.value(), service.newInstance());
				else
					addServiceMap(LuckyUtils.TableToClass1(service.getSimpleName()), service.newInstance());
			}
		}
		return this;
	}
}
