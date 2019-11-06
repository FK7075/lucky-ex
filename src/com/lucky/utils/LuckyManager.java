package com.lucky.utils;

import com.lucky.sqldao.DBConnectionPool;
import com.lucky.sqldao.SqlOperation;

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
