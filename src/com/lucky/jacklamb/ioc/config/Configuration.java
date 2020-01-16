package com.lucky.jacklamb.ioc.config;

import com.lucky.jacklamb.utils.LuckyUtils;

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
		try {
			Class<?> applicationClass=Class.forName("app.appconfig");
			if(!ApplicationConfig.class.isAssignableFrom(applicationClass))
				throw new RuntimeException("app.appconfig配置类必须继承com.lucky.jacklamb.ioc.config.ApplicationConfig类！");
			System.err.println(LuckyUtils.showtime()+"[HELPFUL HINTS]  发现app.appconfig配置类，将使用类中的配置初始化LUCKY...");
			ApplicationConfig appconfig=(ApplicationConfig)applicationClass.newInstance();
			appconfig.init(scancfg, webcfg, servercfg);
		} catch (ClassNotFoundException e) {
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
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
