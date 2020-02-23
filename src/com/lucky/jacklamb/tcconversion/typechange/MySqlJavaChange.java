package com.lucky.jacklamb.tcconversion.typechange;

import com.lucky.jacklamb.exception.NotSupportDataBasesException;

public class MySqlJavaChange extends TypeConversion {


	@Override
	public String javaTypeToDb(String javaType) {
		if("int".equals(javaType)||"Integer".equals(javaType)||"long".equalsIgnoreCase(javaType))
			return "int";
		if("double".equalsIgnoreCase(javaType)||"float".equalsIgnoreCase(javaType))
			return "double";
		if("String".equalsIgnoreCase(javaType))
			return "varchar";
		if("boolean".equalsIgnoreCase(javaType))
			return "bit";
		if("byte[]".equals(javaType))
			return "blob";
		if("Timestamp".equals(javaType))
			return "datetime";
		if("Time".equals(javaType))
			return "time";
		if("Date".equals(javaType))
			return "date";
		throw new NotSupportDataBasesException("LuckyĿǰ����֧�ָ�Java�ĸ������ֶ���Mysql���ݿ��ֶε�ת��"+javaType);
	}

}
