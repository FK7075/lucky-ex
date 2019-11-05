package com.lucky.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucky.enums.PrimaryType;
import com.lucky.ioc.DataSource;
import com.lucky.sqldao.PojoManage;
import com.lucky.sqldao.SqlOperation;

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
	public static ResultSet getResultSet(String sql,Object...obj) {
		SqlOperation sqlop=LuckyManager.getSqlOperation();
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
	 * MySql类型转Java类型
	 * @param trpe MySql类型
	 * @return Java类型
	 */
	public static String mysqlToJava(String trpe) {
		switch (trpe) {
		case "int":
			return "Integer";
		case "double":
			return "Double";
		case "varchar":
			return "String";
		case "datetime":
			return "Date";
		case "date":
			return "Date";
		default:
			return "String";
		}
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
	
	/**
	 * 类型转换
	 * 
	 * @param goal
	 * @param type
	 * @return
	 */
	public static Object typeCast(String goal, String type) {
		switch (type) {
		case "int":
			return Integer.parseInt(goal);
		case "Integer":
			return Integer.parseInt(goal);
		case "Double":
			return Double.parseDouble(goal);
		case "double":
			return Double.parseDouble(goal);
		case "long":
			return Long.parseLong(goal);
		case "Long":
			return Long.parseLong(goal);
		case "float":
			return Float.parseFloat(goal);
		case "Float":
			return Float.parseFloat(goal);
		case "byte":
			return Byte.parseByte(goal);
		case "Byte":
			return Byte.parseByte(goal);
		case "boolean":
			return Boolean.parseBoolean(goal);
		case "Boolean":
			return Boolean.parseBoolean(goal);
		default:
			return goal;
		}
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
	     id="["+sf.format(date)+"]->";
	     return id;
	}
	
	//获得当前数据库的名称
	public static String getDatabaseName() {
		String url = DataSource.getDataSource().getUrl();
		String databasename=url.substring((url.lastIndexOf("/")+1),url.length());
		if(databasename.contains("?")) {
			databasename=databasename.substring(0, databasename.indexOf("?"));
		}
		return databasename;
	}
	
	//设置自增主键的值到Pojo中
	public static void pojoSetId(Object pojo) {
		int next_id=0;
		Class<?> clzz=pojo.getClass();
		if(PojoManage.getIdType(clzz)==PrimaryType.AUTO_INT) {
			String sql="SELECT auto_increment as nextID FROM information_schema.`TABLES` WHERE table_name=? AND TABLE_SCHEMA=?";
			ResultSet resultSet = getResultSet(sql,PojoManage.getTable(clzz),getDatabaseName());
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
	
	public static void welcome() {
		String d="     ,---------------------------------,      ,---------,\r\n" + 
				"         ,-----------------------,          ,\"        ,\"|\r\n" + 
				"      ,\"                      ,\" |        ,\"        ,\"  |\r\n" + 
				"      +-----------------------+  |      ,\"        ,\"    |\r\n" + 
				"      |  .-----------------.  |  |     +---------+      |\r\n" + 
				"      |  |                 |  |  |     | -==----'|      |\r\n" + 
				"      |  |  Lucky          |  |  |     |         |      |\r\n" + 
				"      |  |  version: 1.0.0 |  |  |/----|`---=    |      |\r\n" + 
				"      |  |  C:\\>_          |  |  |   ,/|==== ooo |      ;\r\n" + 
				"      |  |                 |  |  |  // | ((FK))  |    ,\"\r\n" + 
				"      |  `-----------------'  |,\" .;'| |((7075)) |  ,\"\r\n" + 
				"      +-----------------------+  ;;  | |         |,\"\r\n" + 
				"         /_)______________(_/  //'   | +---------+\r\n" + 
				"    ___________________________/___  `,\r\n" + 
				"   /  oooooooooooooooo  .o.  oooo /,   \\,\"-----------\r\n" + 
				"  / ==ooooooooooooooo==.o.  ooo= //   ,`\\--{:)     ,\"\r\n" + 
				" /_==__==========__==_ooo__ooo=_/'   /___________,\"\r\n" + 
				"——————————————————————————————————\r\n\n--------------------------------------------------\n##\n## Lucky !\n## (v1.0.0.RELEASE)\n##\n--------------------------------------------------\n\n" ;
		System.out.println(d);
	}
	
	public static void main(String[] args) {
		welcome();
	}

}
