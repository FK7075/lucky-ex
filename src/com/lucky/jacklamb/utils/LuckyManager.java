package com.lucky.jacklamb.utils;

import com.lucky.jacklamb.sqlcore.SqlOperation;

public class LuckyManager {
//	public static DBConnectionPool dbpool;
	

//	/**
//	 * ����ģʽ�������ӳض���
//	 * @return
//	 */
//	public static DBConnectionPool getDBPool() {
//		if(dbpool==null)
//			dbpool=new DBConnectionPool();
//		return dbpool;
//	}
	/**
	 * ���SqlOperation����
	 * @return
	 */
	public static SqlOperation getSqlOperation(String name) {
		return new SqlOperation(name);
	}
	
}
