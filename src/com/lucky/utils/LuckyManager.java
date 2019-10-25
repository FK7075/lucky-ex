package com.lucky.utils;

import java.net.URL;

import com.lucky.sqldao.DBConnectionPool;
import com.lucky.sqldao.SqlOperation;
import com.lucky.xml.Configuration;
import com.lucky.xml.LuckyXmlConfig;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	
	/**
	 * ����������Ϣ����
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
			throw new RuntimeException("��classpath���Ҳ���lucky.xml�����ļ�,��appconfig����Ҳ�Ҳ�����׼������application.java...");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("�޷�������׼������application...");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("�޷�������׼������application...");
		}
	}
	/**
	 * ����ģʽ�������ӳض���
	 * @return
	 */
	public static DBConnectionPool getDBPool() {
		if(dbpool==null)
			dbpool=new DBConnectionPool();
		return dbpool;
	}
	/**
	 * ���SqlOperation����
	 * @return
	 */
	public static SqlOperation getSqlOperation() {
		return new SqlOperation();
	}
	
}
