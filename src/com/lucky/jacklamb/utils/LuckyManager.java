package com.lucky.jacklamb.utils;

import com.lucky.jacklamb.sqlcore.SqlOperation;

public class LuckyManager {
//	public static DBConnectionPool dbpool;
	

//	/**
//	 * 单例模式返回连接池对象
//	 * @return
//	 */
//	public static DBConnectionPool getDBPool() {
//		if(dbpool==null)
//			dbpool=new DBConnectionPool();
//		return dbpool;
//	}
	/**
	 * 获得SqlOperation对象
	 * @return
	 */
	public static SqlOperation getSqlOperation(String name) {
		return new SqlOperation(name);
	}
	
}
