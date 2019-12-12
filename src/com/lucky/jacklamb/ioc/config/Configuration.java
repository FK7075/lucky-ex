package com.lucky.jacklamb.ioc.config;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.ioc.PackageScan;

public class Configuration {
	
	private static ScanConfig scancfg;
	
	private static WebConfig webcfg;
	
	private static ServerConfig servercfg;
	
	static {
		scancfg = ScanConfig.defaultScanConfig();
		webcfg=WebConfig.defauleWebConfig();
		servercfg=ServerConfig.defaultServerConfig();
		PackageScan ps = PackageScan.getPackageScan();
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
	
	public static ScanConfig getScanConfig() {
		return scancfg;
	}
	
	public static WebConfig getWebConfig() {
		return webcfg;
	}
	
	public static ServerConfig getServerConfig() {
		return servercfg;
	}

}
