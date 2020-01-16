package com.lucky.jacklamb.ioc.config;

public abstract class ApplicationConfig {
	
	protected final void init(ScanConfig scan,WebConfig web,ServerConfig server) {
		scanPackConfig(scan);
		webConfig(web);
		serverConfig(server);
	}
	
	
	/**
	 * ��ɨ���������
	 * @param scan
	 */
	protected void scanPackConfig(ScanConfig scan) {
		
	}
	
	/**
	 * Web���������
	 * @param web
	 */
	protected void webConfig(WebConfig web) {
		
	}
	
	/**
	 * ��Ƕtoncat������������
	 * @param server
	 */
	protected void serverConfig(ServerConfig server) {
		
	}

}
