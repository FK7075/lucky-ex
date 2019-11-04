package com.lucky.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerIOC {
	
	private Map<String,Object> controllerMap;
	
	private List<String> controllerIDS;

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
	
	

}
