package com.lucky.jacklamb.sqldao;

public class SqlCoreFactory {
	
	/**
	 * ���SqlCore����
	 * @return
	 */
	public static SqlCore createSqlCore(String...dbname) {
		return SqlControl.getSqlControl(dbname);
	}

}
