package com.lucky.jacklamb.ioc.config;

public abstract class ApplicationConfig {
	
	/**
	 * ��ɨ���������
	 * @param scan
	 */
	public void scanPackConfig(ScanConfig scan) {
		
	}
	
	/**
	 * ����Դ�������
	 * @param data
	 */
	public void setDataSource(DataSource data) {
	}
	
	/**
	 * Web���������
	 * @param web
	 */
	public void webConfig(WebConfig web) {
		
	}

}
