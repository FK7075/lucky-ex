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
				throw new RuntimeException("app.appconfig���������̳�com.lucky.jacklamb.ioc.config.ApplicationConfig�࣡");
			System.err.println(LuckyUtils.showtime()+"[HELPFUL HINTS]  ����app.appconfig�����࣬��ʹ�����е����ó�ʼ��LUCKY...");
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
