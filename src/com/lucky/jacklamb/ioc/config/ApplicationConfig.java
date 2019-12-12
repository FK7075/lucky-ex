package com.lucky.jacklamb.ioc.config;

public abstract class ApplicationConfig {
	
	/**
	 * 包扫描相关配置
	 * @param scan
	 */
	public void scanPackConfig(ScanConfig scan) {
		
	}
	
	/**
	 * Web层相关配置
	 * @param web
	 */
	public void webConfig(WebConfig web) {
		
	}
	
	/**
	 * 内嵌toncat服务器的配置
	 * @param server
	 */
	public void serverConfig(ServerConfig server) {
		
	}

}
