package com.lucky.jacklamb.ioc.config;

public abstract class ApplicationConfig {
	
	/**
	 * 包扫描相关配置
	 * @param scan
	 */
	public void scanPackConfig(ScanConfig scan) {
		
	}
	
	/**
	 * 数据源相关配置
	 * @param data
	 */
	public void setDataSource(DataSource data) {
	}
	
	/**
	 * Web层相关配置
	 * @param web
	 */
	public void webConfig(WebConfig web) {
		
	}

}
