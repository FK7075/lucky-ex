package com.lucky.jacklamb.sqlcore;

public class SqlCoreFactory {
	
	/**
	 * 获得SqlCore对象
	 * @return
	 */
	public static SqlCore createSqlCore(String...dbname) {
		return SqlControl.getSqlControl(dbname);
	}

}
