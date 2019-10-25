package com.lucky.utils;

import java.net.URL;

import com.lucky.sqldao.DBConnectionPool;
import com.lucky.sqldao.SqlOperation;
import com.lucky.xml.Configuration;
import com.lucky.xml.LuckyXmlConfig;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	
	/**
	 * 返回配置信息对象
	 * @return
	 */
	public static ProperConfig getPropCfg() {
		try {
			URL resource = LuckyManager.class.getClassLoader().getResource("lucky.xml");
			if(resource!=null)
				return LuckyXmlConfig.loadLuckyXmlConfig().getProper();
			Class<?> cfgClass=Class.forName("appconfig.application");
			Configuration cfg=(Configuration) cfgClass.newInstance();
			return cfg.getProperConfig();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("在classpath下找不到lucky.xml配置文件,在appconfig包中也找不到标准配置类application.java...");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("无法创建标准配置类application...");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("无法创建标准配置类application...");
		}
	}
	/**
	 * 单例模式返回连接池对象
	 * @return
	 */
	public static DBConnectionPool getDBPool() {
		if(dbpool==null)
			dbpool=new DBConnectionPool();
		return dbpool;
	}
	/**
	 * 获得SqlOperation对象
	 * @return
	 */
	public static SqlOperation getSqlOperation() {
		return new SqlOperation();
	}
	
}
