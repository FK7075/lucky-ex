package com.lucky.jacklamb.ioc.config;

import com.lucky.jacklamb.file.ini.IniFilePars;
import com.lucky.jacklamb.ioc.scan.ScanFactory;
import com.lucky.jacklamb.utils.LuckyUtils;

public class AppConfig {

	private static AppConfig appConfig;

	private ScanConfig scancfg;

	private WebConfig webcfg;

	private ServerConfig servercfg;
	
	public static Class<?> applicationClass;

	private static boolean isFirst = true;

	private AppConfig() {
		scancfg = ScanConfig.defaultScanConfig();
		webcfg = WebConfig.defauleWebConfig();
		servercfg = ServerConfig.defaultServerConfig();
		ApplicationConfig appconfig = ScanFactory.createScan().getApplicationConfig();
		if(isFirst) {
			if (appconfig != null) {
				System.err.println(LuckyUtils.showtime() + "[HELPFUL HINTS]  ����������" + appconfig.getClass().getName()
						+ "����ʹ�����е����ó�ʼ��LUCKY...");
				appconfig.init(scancfg, webcfg, servercfg);
			}
			new IniFilePars().modifyAllocation(scancfg, webcfg, servercfg);
			isFirst = false;
		}
	}

	public static AppConfig getAppConfig() {
		if (appConfig == null)
			appConfig = new AppConfig();
		return appConfig;
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
	
	/**
	 * �õ�Ĭ��״̬�µ�WebConfig
	 * @return
	 */
	public static WebConfig getDefaultWebConfig() {
		return WebConfig.defauleWebConfig();
	}
	
	/**
	 * �õ�Ĭ��״̬�µ�ScanConfig
	 * @return
	 */
	public static ScanConfig getDefaultScanConfig() {
		return ScanConfig.defaultScanConfig();
	}
	
	/**
	 * �õ�Ĭ��״̬�µ�ServerConfig
	 * @return
	 */
	public static ServerConfig getDefaultServerConfig() {
		return ServerConfig.defaultServerConfig();
	}

}
