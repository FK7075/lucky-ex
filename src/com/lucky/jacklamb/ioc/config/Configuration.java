package com.lucky.jacklamb.ioc.config;

import com.lucky.jacklamb.file.ini.IniFilePars;
import com.lucky.jacklamb.ioc.ScanFactory;
import com.lucky.jacklamb.utils.LuckyUtils;

public class Configuration {

	private static Configuration configuration;

	private ScanConfig scancfg;

	private WebConfig webcfg;

	private ServerConfig servercfg;
	
	public static Class<?> applicationClass;

	private static boolean isFirst = true;

	private Configuration() {
		scancfg = ScanConfig.defaultScanConfig();
		webcfg = WebConfig.defauleWebConfig();
		servercfg = ServerConfig.defaultServerConfig();
		ApplicationConfig appconfig = ScanFactory.createScan().getApplicationConfig();
		if(isFirst) {
			if (appconfig != null) {
				System.err.println(LuckyUtils.showtime() + "[HELPFUL HINTS]  发现配置类" + appconfig.getClass().getName()
						+ "，将使用类中的配置初始化LUCKY...");
				appconfig.init(scancfg, webcfg, servercfg);
			}
			new IniFilePars().modifyAllocation(scancfg, webcfg, servercfg);
			isFirst = false;
		}
	}

	public static Configuration getConfiguration() {
		if (configuration == null)
			configuration = new Configuration();
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
