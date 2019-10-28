package com.lucky.xml;

import java.util.ArrayList;
import java.util.List;

public abstract class Configuration {
	
	/**
	 * �����й�Orm������
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
	 * �����й�MVC������
	 * @param mvc
	 * @param mapping
	 */
	public void loadMvcSetting(MvcXmlModel mvc,List<List<LuckyMapping>> mapping) {
		mvc.setEncoding("ISO-8859-1");
	}
	
	/**
	 * �����й�IOC Beans������
	 * @param beans
	 */
	public void loadBeanSetting(List<LuckyXml> beans) {
		
	}
	
	

}
