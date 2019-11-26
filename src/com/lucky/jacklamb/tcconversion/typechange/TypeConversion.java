package com.lucky.jacklamb.tcconversion.typechange;

import com.lucky.jacklamb.exception.NotSupportDataBasesException;

public abstract class TypeConversion {

	/**
	 * �����ݿ�����ת��Ϊjava���͵��ַ�����ʾ(�������򹤳�)
	 * @param datype ���ݿ�����
	 * @return
	 */
	public String dbTypeToJava(String datype) {
		if("varchar".equalsIgnoreCase(datype)||"char".equalsIgnoreCase(datype)||"text".equalsIgnoreCase(datype)
		 ||"enum".equalsIgnoreCase(datype)||"set".equalsIgnoreCase(datype)||"graphic ".equalsIgnoreCase(datype)
		 ||"clob".equalsIgnoreCase(datype)||"dbclob".equalsIgnoreCase(datype)||"long varchar".equalsIgnoreCase(datype)
		 ||"long vargraphic".equalsIgnoreCase(datype)||"vargraphic".equalsIgnoreCase(datype)||"tinytext".equalsIgnoreCase(datype))
			return "String";
		if("tinyint".equalsIgnoreCase(datype)||"smallint".equalsIgnoreCase(datype)||"mediumint".equalsIgnoreCase(datype)
		 ||"int".equalsIgnoreCase(datype)||"integer".equalsIgnoreCase(datype)||"number".equalsIgnoreCase(datype))
			return "Integer";
		if("double".equalsIgnoreCase(datype)||"precision".equalsIgnoreCase(datype)||"float".equalsIgnoreCase(datype))
			return "Double";
		if("bigint".equalsIgnoreCase(datype))
			return "Long";
		if("blob".equalsIgnoreCase(datype)||"image".equalsIgnoreCase(datype)||"long raw".equalsIgnoreCase(datype)
		 ||"binary".equalsIgnoreCase(datype)||"varbinary".equalsIgnoreCase(datype)||"raw".equalsIgnoreCase(datype))
			return "byte[]";
		if("bit".equalsIgnoreCase(datype)||"n/a".equalsIgnoreCase(datype))
			return "Boolean";
		if("decimal".equalsIgnoreCase(datype)||"numeric".equalsIgnoreCase(datype)||"money".equalsIgnoreCase(datype)
		 ||"smallmoney".equalsIgnoreCase(datype))
			return "BigDecimal";
		if("date".equalsIgnoreCase(datype)||"year".equalsIgnoreCase(datype))
			return "Date";
		if("time".equalsIgnoreCase(datype))
			return "Time";
		if("datetime".equalsIgnoreCase(datype)||"smalldatetime".equalsIgnoreCase(datype)||"timestamp".equalsIgnoreCase(datype))
			return "Timestamp";
		throw new NotSupportDataBasesException("LuckyĿǰ����֧�ָ����ݿ�ĸ������ֶε�ת��"+datype);
	}
	
	/**
	 * ��java����ת��Ϊ���ݿ����͵��ַ�����ʾ(�����Զ�����)
	 * @param javaType javaClass
	 * @return
	 */
	public abstract String javaTypeToDb(String javaType);


}
