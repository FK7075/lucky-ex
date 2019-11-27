package com.lucky.jacklamb.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucky.jacklamb.enums.PrimaryType;
import com.lucky.jacklamb.sqlcore.PojoManage;
import com.lucky.jacklamb.sqlcore.ReadProperties;
import com.lucky.jacklamb.sqlcore.SqlOperation;

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
	
	private static List<String> list=new ArrayList<>();
	
	public static List<String> getSqlField(String nosql){
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
	     Date date=new Date();
	     SimpleDateFormat sf=
	    	 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     id="["+sf.format(date)+"]  ";
	     return id;
	}
	
	//��õ�ǰ���ݿ������
	public static String getDatabaseName(String dbname) {
		String url = ReadProperties.getDataSource(dbname).getJdbcUrl();
		String databasename=url.substring((url.lastIndexOf("/")+1),url.length());
		if(databasename.contains("?")) {
			databasename=databasename.substring(0, databasename.indexOf("?"));
		}
		return databasename;
	}
	
	public static String getDatabaseType(String dbname) {
		String jdbcDriver=ReadProperties.getDataSource(dbname).getDriverClass();
		if(jdbcDriver.contains("mysql"))
			return "MySql";
		if(jdbcDriver.contains("db2"))
			return "DB2";
		if(jdbcDriver.contains("oracle"))
			return "Oracle";
		if(jdbcDriver.contains("postgresql"))
			return "PostgreSql";
		if(jdbcDriver.contains("sqlserver"))
			return "Sql Server";
		if(jdbcDriver.contains("sybase"))
			return "Sybase";
		return null;
	}
	
	//��õ�ǰ���ݿ������
	public static void pojoSetId(String dbname,Object pojo) {
		int next_id=0;
		Class<?> clzz=pojo.getClass();
		if(PojoManage.getIdType(clzz)==PrimaryType.AUTO_INT) {
			String sql="SELECT auto_increment as nextID FROM information_schema.`TABLES` WHERE table_name=? AND TABLE_SCHEMA=?";
			ResultSet resultSet = getResultSet(sql,PojoManage.getTable(clzz),getDatabaseName(dbname));
			try {
				while(resultSet.next()) {
					String nextID= resultSet.getString("nextID");
					next_id=Integer.parseInt(nextID);
				}
				Field field=PojoManage.getIdField(clzz);
				field.setAccessible(true);
				field.set(pojo, next_id-1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
