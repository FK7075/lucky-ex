package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.ScanConfig;

public abstract class Scan {
	
	protected Map<String, List<Class<?>>> componentClassMap;
	
	protected List<Class<?>> controllerClass;
	
	protected List<Class<?>> serviceClass;
	
	protected List<Class<?>> repositoryClass;
	
	protected List<Class<?>> componentClass;
	
	protected List<Class<?>> aspectClass;
	
	private Configuration configuration;
	
	public Scan() {
		componentClassMap=new HashMap<>();
		controllerClass=new ArrayList<>();
		serviceClass=new ArrayList<>();
		repositoryClass=new ArrayList<>();
		componentClass=new ArrayList<>();
		aspectClass=new ArrayList<>();
	}
	
	public void init() {
		configuration=Configuration.getConfiguration();
		if(configuration.getScanConfig().isAutoScan()) {
			System.err.println("SCAN-MODE£º[AUTO SCAN]");
			autoScan();
		}else {
			System.err.println("SCAN-MODE: [SUFFIX SCAN]");
			suffixScan();
		}
		componentClassMap.put("controller", controllerClass);
		componentClassMap.put("service", serviceClass);
		componentClassMap.put("repository", repositoryClass);
		componentClassMap.put("component", componentClass);
		componentClassMap.put("aspect", aspectClass);
	}
	
	public List<Class<?>> getComponentClass(String iocCode){
		return componentClassMap.get(iocCode);
	}
	
	public void  suffixScan() {
		ScanConfig scanConfig = configuration.getScanConfig();
		controllerClass=loadComponent(scanConfig.getControllerPackSuffix());
		serviceClass=loadComponent(scanConfig.getServicePackSuffix());
		repositoryClass=loadComponent(scanConfig.getRepositoryPackSuffix());
		componentClass=loadComponent(scanConfig.getComponentPackSuffix());
		aspectClass=loadComponent(scanConfig.getAspectPackSuffix());
	}
	
	public abstract List<Class<?>> loadComponent(List<String> suffixs);
	
	public abstract void autoScan();
	

}
