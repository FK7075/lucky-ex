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
 * Lucky的工具类
 * @author fk-7075
 *
 */
public class LuckyUtils {
	/**
	 * 单词的首字母大写
	 * @param tableName 原始单词
	 * @return 首字母变大写后的单词
	 */
	public static String TableToClass(String tableName) {
		return tableName.toUpperCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	
	/**
	 * 单词的首字母小写
	 * @param tableName 原始单词
	 * @return 首字母变小写后的单词
	 */
	public static String TableToClass1(String tableName) {
		return tableName.toLowerCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	/**
	 * 快速得到结果集
	 * @param sql 预编译的SQL
	 * @param obj 填充占位符的对象数组
	 * @return 查询结果集
	 */
	public static ResultSet getResultSet(String name,String sql,Object...obj) {
		SqlOperation sqlop=LuckyManager.getSqlOperation(name);
		return sqlop.getResultSet(sql, obj);
	}
	/**
	 * 获得属性的类型去掉长度
	 * @param type 带长度的属性类型
	 * @return 不带长度的属性
	 */
	public static String getMySqlType(String type) {
		if(type.indexOf("(")>=0)
			return type.substring(0, type.indexOf("("));
		else
			return type;
	}
	
	
	/**
	 * 将用','分隔的字符串截取为集合
	 * @param 用','分隔的字符串
	 * @return list集合
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
	
	//获得当前数据库的名称
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
	
	//获得当前数据库的类型
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
	 * 判断该类型是否为java类型
	 * @param clzz
	 * @return
	 */
	public static boolean isJavaClass(Class<?> clzz) {
		return clzz!=null&&clzz.getClassLoader()==null;
	}
	
}
