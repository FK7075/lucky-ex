package com.lucky.xml;

import java.util.List;

import com.lucky.utils.ProperConfig;

public abstract class Configuration {
	
	public void loadOrmSetting(ProperConfig orm) {
		orm.setLog(false);
		orm.setPoolmin(10);
		orm.setPoolmax(100);
		orm.setCache(false);
		
	}
	
	public void loadMvcSetting(MvcXmlModel mvc,List<List<LuckyMapping>> mapping) {
		
	}
	
	public void loadBeanSetting(List<LuckyXml> beans) {
		
	}
	
	

}
