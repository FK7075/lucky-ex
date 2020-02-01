package com.lucky.jacklamb.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.ioc.config.ApplicationConfig;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.ioc.config.ScanConfig;
import com.lucky.jacklamb.utils.LuckyUtils;

public abstract class Scan {
	
	
	protected Map<String, List<Class<?>>> componentClassMap;
	
	protected List<Class<?>> controllerClass;
	
	protected List<Class<?>> serviceClass;
	
	protected List<Class<?>> repositoryClass;
	
	protected List<Class<?>> componentClass;
	
	protected List<Class<?>> aspectClass;
	
	protected ApplicationConfig appConfig;
	
	private Configuration configuration;
	
	protected boolean isFirst=true;
	
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
		if(configuration.getScanConfig().getScanMode()==com.lucky.jacklamb.enums.Scan.AUTO_SCAN) {
			System.err.println(LuckyUtils.showtime()+"[ SCAN-MODE                 ~ ]  AUTO_SCAN");
			autoScan();
		}else {
			System.err.println(LuckyUtils.showtime()+"[ SCAN-MODE                 - ]  SUFFIX_SCAN");
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
	
	public ApplicationConfig getApplicationConfig() {
		if(isFirst) {
			findAppConfig();
			isFirst=false;
		}
		return appConfig;
	}
	
	/**
	 * ’“µΩApplicationConfig≈‰÷√¿‡
	 */
	public abstract void findAppConfig();
	
	/**
	 * ∫Û◊∫…®√Ë
	 * @param suffixs
	 * @return
	 */
	public abstract List<Class<?>> loadComponent(List<String> suffixs);
	
	/**
	 * ◊‘∂Ø…®√Ë
	 */
	public abstract void autoScan();
	
	

}
