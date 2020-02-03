package com.lucky.jacklamb.utils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucky.jacklamb.sqlcore.c3p0.SqlOperation;

/**
 * Lucky�Ĺ�����
 * @author fk-7075
 *
 */
public class LuckyUtils {
	/**
	 * ���ʵ�����ĸ��д
	 * @param tableName ԭʼ����
	 * @return ����ĸ���д��ĵ���
	 */
	public static String TableToClass(String tableName) {
		return tableName.toUpperCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	
	/**
	 * ���ʵ�����ĸСд
	 * @param tableName ԭʼ����
	 * @return ����ĸ��Сд��ĵ���
	 */
	public static String TableToClass1(String tableName) {
		return tableName.toLowerCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	/**
	 * ���ٵõ������
	 * @param sql Ԥ�����SQL
	 * @param obj ���ռλ���Ķ�������
	 * @return ��ѯ�����
	 */
	public static ResultSet getResultSet(String name,String sql,Object...obj) {
		SqlOperation sqlop=LuckyManager.getSqlOperation(name);
		return sqlop.getResultSet(sql, obj);
	}
	/**
	 * ������Ե�����ȥ������
	 * @param type �����ȵ���������
	 * @return �������ȵ�����
	 */
	public static String getMySqlType(String type) {
		if(type.indexOf("(")>=0)
			return type.substring(0, type.indexOf("("));
		else
			return type;
	}
	
	
	/**
	 * ����','�ָ����ַ�����ȡΪ����
	 * @param ��','�ָ����ַ���
	 * @return list����
	 */
	public static List<String> strToArray(String str) {
		List<String> list=new ArrayList<>();
		String[] strArray=str.split(",");
		for (String s : strArray) {
			list.add(s);
		}
		return list;
	}
	
	
	public static String getSqlStatem(String nosql) {
		if(nosql.contains("#{")) {
			int start=nosql.indexOf("#");
			int end=nosql.indexOf("}")+1;
			String sub=nosql.substring(start, end);
			nosql=nosql.replace(sub, "?");
			nosql=getSqlStatem(nosql);
		}
		return nosql;
	}
	
	public static List<String> getSqlField(String nosql){
		List<String> list=new ArrayList<>();
		while(nosql.contains("#{")) {
			int start=nosql.indexOf("#{")+2;
			int end=nosql.indexOf("}");
			String field=nosql.substring(start, end);
			list.add(field);
			nosql=nosql.replaceFirst("#\\{"+field+"\\}", "");
		}
		return list;
	}
	
	
	public static String showtime() {
	     String id=null;
	     id="["+time()+"]  ";
	     return id;
	}
	
	public static String time() {
	     Date date=new Date();
	     SimpleDateFormat sf=
	    	 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     return sf.format(date);
	}
	
	
	/**
	 * �жϸ������Ƿ�Ϊjava����
	 * @param clzz
	 * @return
	 */
	public static boolean isJavaClass(Class<?> clzz) {
		return clzz!=null&&clzz.getClassLoader()==null;
	}
	
}
