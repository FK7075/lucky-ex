package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	
}
