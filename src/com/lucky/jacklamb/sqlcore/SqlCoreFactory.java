package com.lucky.jacklamb.sqlcore;

public class SqlCoreFactory {
	
	/**
	 * ���SqlCore����
	 * @return
	 */
	public static SqlCore createSqlCore(String...dbname) {
		return SqlControl.getSqlControl(dbname);
	}

}
