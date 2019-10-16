package com.lucky.utils;

import com.lucky.sqldao.DBConnectionPool;
import com.lucky.sqldao.SqlOperation;
import com.lucky.xml.LuckyXmlConfig;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	
	/**
	 * 返回配置信息对象
	 * @return
	 */
	public static ProperConfig getPropCfg() {
		return LuckyXmlConfig.loadLuckyXmlConfig().getProper();
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
