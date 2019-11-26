package com.lucky.jacklamb.tcconversion.typechange;

import com.lucky.jacklamb.exception.NotSupportDataBasesException;
import com.lucky.jacklamb.sqlcore.ReadProperties;

public class JDChangeFactory {
	
	/**
	 * ��ȡ���ݿ������ת������
	 * @param jdbcDriver
	 * @return
	 */
	public static TypeConversion jDChangeFactory(String dbname) {
		String jdbcDriver=ReadProperties.getDataSource(dbname).getDriverClass();
		if(jdbcDriver.contains("mysql"))
			return new MySqlJavaChange();
		if(jdbcDriver.contains("db2"))
			return new DB2JavaChange();
		if(jdbcDriver.contains("oracle"))
			return new OracleJavaChange();
		if(jdbcDriver.contains("postgresql"))
			return new PostgreJavaChange();
		if(jdbcDriver.contains("sqlserver"))
			return new SqlServerJavaChange();
		if(jdbcDriver.contains("sybase"))
			return new SyBaseJavaChange();
		throw new NotSupportDataBasesException("Lucky�ֲ�֧�ָ�������Ӧ�����ݿ⣡ driverClass:"+jdbcDriver);
	}

}
