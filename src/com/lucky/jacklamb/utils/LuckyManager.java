package com.lucky.jacklamb.utils;

import com.lucky.jacklamb.sqlcore.c3p0.SqlOperation;

public class LuckyManager {
	
	/**
	 * ���SqlOperation����
	 * @return
	 */
	public static SqlOperation getSqlOperation(String name) {
		return new SqlOperation(name);
	}
	
}
