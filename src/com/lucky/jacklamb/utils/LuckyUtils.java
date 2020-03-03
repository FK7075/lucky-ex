package com.lucky.jacklamb.utils;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	/**
	 * ����ָ���ĸ�ʽ��ȡ��ǰʱ����ַ���
	 * @param format ��ʽ��YYYY-MM-DD HH:MM:SS��
	 * @return
	 */
	public static String time(String format) {
	     Date date=new Date();
	     SimpleDateFormat sf=
	    	 new SimpleDateFormat(format);
	     return sf.format(date);
	}
	
	/**
	 * ��ȡ��ǰʱ��
	 * @return
	 */
	public static String time() {
	     Date date=new Date();
	     SimpleDateFormat sf=
	    	 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     return sf.format(date);
	}
	
	/**
	 * ��Date���ո�ʽת��ΪString
	 * @param date Data����
	 * @param format (eg:yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String time(Date date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * ����ָ����ʽ���ַ���ת��ΪDate����
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateStr,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ʱ������
	 * @param dateStr
	 * @param format
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static Date addDate(String dateStr,String format,int calendarField,int amount) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(getDate(dateStr,format));
		instance.add(calendarField, amount);
		return instance.getTime();
	}
	
	/**
	 * ʱ������
	 * @param dateStr
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static Date addDate(String dateStr,int calendarField,int amount) {
		return addDate(dateStr,"yyyy-MM-dd",calendarField,amount);
	}
	
	/**
	 * ���ڵ�ǰʱ��Ļ����ϵ�ʱ������
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static Date currAddDate(int calendarField,int amount) {
		Calendar instance = Calendar.getInstance();
		instance.add(calendarField, amount);
		return instance.getTime();
	}
	
	
	/**
	 * ������תDate 
	 * @param dateStr (eg:2020-06-31)
	 * @return
	 */
	public static Date getDate(String dateStr) {
		return getDate(dateStr,"yyyy-MM-dd");
	}
	
	/**
	 * ������ʱ����תDate
	 * @param dateTimeStr (eg:2020-06-31 12:23:06)
	 * @return
	 */
	public static Date getDateTime(String dateTimeStr) {
		return getDate(dateTimeStr,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * ��Stringת��Ϊjava.sql.Date
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Date getSqlDate(String dateStr){
		return new java.sql.Date(getDate(dateStr,"yyyy-MM-dd").getTime());
	}
	
	/**
	 * java.sql.Date������
	 * @param dateStr
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static java.sql.Date addSqlDate(String dateStr,int calendarField,int amount){
		return new java.sql.Date(addDate(dateStr,calendarField,amount).getTime());
	}
	
	/**
	 * java.sql.Date������
	 * @param dateStr
	 * @param format
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static java.sql.Date addSqlDate(String dateStr,String format,int calendarField,int amount){
		return new java.sql.Date(addDate(dateStr,format,calendarField,amount).getTime());
	}
	
	/**
	 * ���ڵ�ǰʱ��java.sql.Date������
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static java.sql.Date currAddSqlDate(int calendarField,int amount){
		return new java.sql.Date(currAddDate(calendarField, amount).getTime());
	}
	
	/**
	 * ��ȡ��ǰʱ���java.sql.Date
	 * @return
	 */
	public static java.sql.Date getSqlDate(){
		return new java.sql.Date(new Date().getTime());
	}
	
	/**
	 * ��Stringת��Ϊjava.sql.Time
	 * @param timeStr
	 * @return
	 */
	public static java.sql.Time getSqlTime(String timeStr){
		return new java.sql.Time(getDate(timeStr,"HH:mm:ss").getTime());
	}
	
	/**
	 * ��ȡ��ǰʱ���java.sql.Time
	 * @return
	 */
	public static java.sql.Time getSqlTime(){
		return new java.sql.Time(new Date().getTime());
	}
	
	/**
	 * ��Stringת��Ϊjava.sql.Timestamp
	 * @param timestampStr
	 * @return
	 */
	public static Timestamp getTimestamp(String timestampStr) {
		return new Timestamp(getDate(timestampStr,"yyyy-MM-dd HH:mm:ss").getTime());
	}
	
	/**
	 * ��ȡ��ǰʱ���java.sql.Timestamp
	 * @return
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
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
