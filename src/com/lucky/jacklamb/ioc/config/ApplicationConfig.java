package com.lucky.jacklamb.ioc.config;

public abstract class ApplicationConfig {
	
	/**
	 * ��ɨ���������
	 * @param scan
	 */
	public void scanPackConfig(ScanConfig scan) {
		
	}
	
	/**
	 * Web���������
	 * @param web
	 */
	public void webConfig(WebConfig web) {
		
	}
	
	/**
	 * ��Ƕtoncat������������
	 * @param server
	 */
	public void serverConfig(ServerConfig server) {
		
	}

}
