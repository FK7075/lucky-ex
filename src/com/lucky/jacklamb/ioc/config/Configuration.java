package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.ioc.ScacFactory;
import com.lucky.jacklamb.ioc.Scan;

public class Configuration {
	
	private static Configuration configuration;
	
	private ScanConfig scancfg;
	
	private WebConfig webcfg;
	
	private ServerConfig servercfg;
	
	public static Class<?> applicationClass;
	
	private Configuration() {
		scancfg = ScanConfig.defaultScanConfig();
		webcfg=WebConfig.defauleWebConfig();
		servercfg=ServerConfig.defaultServerConfig();
		Scan ps = ScacFactory.createScan();
		List<String> cfgsuffix = new ArrayList<>();
		cfgsuffix.add("appconfig");
		List<String> cfgClass = ps.loadComponent(cfgsuffix);
		try {
			for (String clzz : cfgClass) {
				Class<?> cfg = Class.forName(clzz);
				if (ApplicationConfig.class.isAssignableFrom(cfg)) {
					ApplicationConfig cfConfig = (ApplicationConfig) cfg.newInstance();
					cfConfig.scanPackConfig(scancfg);
					cfConfig.serverConfig(servercfg);
					cfConfig.webConfig(webcfg);
					break;
				} else {
					continue;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static Configuration getConfiguration() {
		if(configuration==null)
			configuration=new Configuration();
		return configuration;
	}
	
	public ScanConfig getScanConfig() {
		return scancfg;
	}
	
	public WebConfig getWebConfig() {
		return webcfg;
	}
	
	public ServerConfig getServerConfig() {
		return servercfg;
	}

}
