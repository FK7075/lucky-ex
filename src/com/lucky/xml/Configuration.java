package com.lucky.xml;

import java.util.List;

import com.lucky.utils.ProperConfig;

public abstract class Configuration {
	
	/**
	 * 设置有关Orm的配置
	 * @param orm
	 */
	public void loadOrmSetting(ProperConfig orm) {
		orm.setLog(false);
		orm.setPoolmin(10);
		orm.setPoolmax(100);
		orm.setCache(false);
		orm.setDriver("com.mysql.jdbc.Drive");
		orm.setUsername("root");
		orm.setPassword("1234");
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
