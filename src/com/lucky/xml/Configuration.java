package com.lucky.xml;

import java.util.ArrayList;
import java.util.List;

public abstract class Configuration {
	
	/**
	 * 设置有关Orm的配置
	 * @param orm
	 */
	public void loadOrmSetting(DatabaseSetter dbcfg) {
		dbcfg.setDatabaseType("MySql");
		dbcfg.setIp("localhost");
		dbcfg.setPort("3306");
		dbcfg.setPassword("1234");
		dbcfg.setUser("root");
		dbcfg.setDebug(false);
		dbcfg.setCache(false);
		dbcfg.setPoolMin(10);
		dbcfg.setPoolMax(100);
		dbcfg.setMapperPackage(new ArrayList<>());
		dbcfg.setEntityFullPath(new ArrayList<>());
		dbcfg.setMapperPackage(new ArrayList<>());
		dbcfg.setComponentPackage(new ArrayList<>());
	}
	
	/**
	 * 设置有关MVC的配置
	 * @param mvc
	 * @param mapping
	 */
	public void loadMvcSetting(MvcXmlModel mvc,List<List<LuckyMapping>> mapping) {
		mvc.setEncoding("ISO-8859-1");
	}
	
	/**
	 * 设置有关IOC Beans的配置
	 * @param beans
	 */
	public void loadBeanSetting(List<LuckyXml> beans) {
		
	}
	
	

}
