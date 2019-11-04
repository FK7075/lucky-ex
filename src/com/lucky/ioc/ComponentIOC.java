package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	

}
