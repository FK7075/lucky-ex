package com.lucky.utils;

import com.lucky.sqldao.DBConnectionPool;
import com.lucky.sqldao.SqlOperation;
import com.lucky.xml.LuckyXmlConfig;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	
	/**
	 * ����������Ϣ����
	 * @return
	 */
	public static ProperConfig getPropCfg() {
		return LuckyXmlConfig.loadLuckyXmlConfig().getProper();
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
