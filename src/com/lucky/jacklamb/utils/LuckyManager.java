package com.lucky.jacklamb.utils;

import com.lucky.jacklamb.sqldao.DBConnectionPool;
import com.lucky.jacklamb.sqldao.SqlOperation;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	

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
