package com.lucky.jacklamb.tcconversion.createtable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.enums.PrimaryType;
import com.lucky.jacklamb.sqlcore.PojoManage;
import com.lucky.jacklamb.tcconversion.typechange.JDChangeFactory;
import com.lucky.jacklamb.tcconversion.typechange.TypeConversion;

/**
 * 生成建表语句的类
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("unchecked")
public class CreateTableSql {

	/**
	 * 根据类的Class信息生成建表语句
	 * @param clzz 目标类的Class
	 * @return
	 */
	public static String getCreateTable(String dbname,Class<?> clzz) {
		TypeConversion jDChangeFactory = JDChangeFactory.jDChangeFactory(dbname);
		String sql = "CREATE TABLE IF NOT EXISTS " + PojoManage.getTable(clzz)+ " (";
		Field[] fields = clzz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldType=fields[i].getType().getSimpleName();
			if (i < fields.length - 1) {
				if (PojoManage.getTableField(fields[i]).equals(PojoManage.getIdString(clzz)))
					sql += PojoManage.getIdString(clzz) + " " + jDChangeFactory.javaTypeToDb(fieldType) + "("+PojoManage.getLength(fields[i])+")"
							+ " NOT NULL "+isAutoInt(clzz)+" PRIMARY KEY,";
				else if (!("double".equals(jDChangeFactory.javaTypeToDb(fieldType))
						|| "datetime".equals(jDChangeFactory.javaTypeToDb(fieldType))
						|| "date".equals(jDChangeFactory.javaTypeToDb(fieldType)))) {
					sql += PojoManage.getTableField(fields[i]) + " " + jDChangeFactory.javaTypeToDb(fieldType) + "("+PojoManage.getLength(fields[i])+") "
							+ allownull(fields[i])+",";
				} else {
					sql += PojoManage.getTableField(fields[i]) + " " + jDChangeFactory.javaTypeToDb(fieldType) + allownull(fields[i])+",";
				}
			} else {
				if (PojoManage.getTableField(fields[i]).equals(PojoManage.getIdString(clzz)))
					sql += PojoManage.getTableField(fields[i]) + " " + jDChangeFactory.javaTypeToDb(fieldType) + "("+PojoManage.getLength(fields[i])+")"
							+ " NOT NULL AUTO_INCREMENT PRIMARY KEY";
				else if (!("double".equals(jDChangeFactory.javaTypeToDb(fieldType))
						|| "datetime".equals(jDChangeFactory.javaTypeToDb(fieldType))
						|| "date".equals(jDChangeFactory.javaTypeToDb(fieldType)))) {
					sql += PojoManage.getTableField(fields[i]) + " " + jDChangeFactory.javaTypeToDb(fieldType) + "("+PojoManage.getLength(fields[i])+") "
							+allownull(fields[i]);
				} else {
					sql += PojoManage.getTableField(fields[i]) + " " + jDChangeFactory.javaTypeToDb(fieldType) + allownull(fields[i]);
				}
			}
		}
		sql += ") ENGINE=InnoDB DEFAULT CHARSET=UTF8";
		return sql;
	}

	/**
	 * 生成添加外键的sql语句集合
	 * @param clzz
	 * 目标类的Class
	 * @return
	 */
	public static List<String> getForeignKey(Class<?> clzz) {
		List<String> stlist = new ArrayList<String>();
		List<Field> keys = (List<Field>) PojoManage.getKeyFields(clzz, true);
		if (keys.isEmpty()) {
			return null;
		} else {
			List<Class<?>> cs = (List<Class<?>>) PojoManage.getKeyFields(clzz, false);
			for (int i = 0; i < cs.size(); i++) {
				String sql = "ALTER TABLE " + PojoManage.getTable(clzz) + " ADD CONSTRAINT " + getRandomStr()
						+ " FOREIGN KEY (" + PojoManage.getTableField(keys.get(i)) + ") REFERENCES " + PojoManage.getTable(cs.get(i)) + "("
						+ PojoManage.getIdString(cs.get(i)) + ")"+isCascadeDel(cs.get(i))+isCascadeUpd(cs.get(i));
				stlist.add(sql);
			}
			return stlist;
		}
	}
	
	/**
	 * 生成添加索引的sql语句集合
	 * @param clzz
	 * @return
	 */
	public static List<String> getIndexKey(Class<?> clzz){
		String table_name=PojoManage.getTable(clzz);
		List<String> indexlist = new ArrayList<String>();
		String primary = PojoManage.primary(clzz);
		String[] indexs = PojoManage.index(clzz);
		String[] fulltextes = PojoManage.fulltext(clzz);
		String[] uniques = PojoManage.unique(clzz);
		if(!"".equals(primary)){
			String p_key="ALTER TABLE "+table_name+" ADD PRIMARY KEY("+primary+")";
			indexlist.add(p_key);
		}
		addAll(indexlist,table_name,indexs,"INDEX");
		addAll(indexlist,table_name,fulltextes,"FULLTEXT");
		addAll(indexlist,table_name,uniques,"UNIQUE");
		return indexlist;
	}

	/**
	 * 拼接该实体中需要配置的所有索引信息
	 * @param indexlist
	 * @param tablename
	 * @param indexs
	 * @param type
	 */
	private static void addAll(List<String> indexlist, String tablename, String[] indexs, String type) {
		String key="ALTER TABLE "+tablename+" ADD ";
		for(String index:indexs) {
			String indexkey;
			if("INDEX".equals(type))
				indexkey=key+type+" "+getRandomStr()+"(";
			else
				indexkey=key+type+"(";
			 indexkey+=index+")";
			indexlist.add(indexkey);
		}
		
	}

	/**
	 * 生成外键名
	 * @return
	 */
	private static String getRandomStr() {
		String[] st = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z" };
		int[] i = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, };
		int a = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int b = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int c = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		int x = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int y = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int z = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		String m = st[a] + st[b] + st[x] + st[y] + i[z] + i[c];
		return m;
	}
	
	/**
	 * 设置主键类型
	 * @param clzz
	 * @return
	 */
	private static String isAutoInt(Class<?> clzz) {
		PrimaryType idType = PojoManage.getIdType(clzz);
		if(idType==PrimaryType.AUTO_INT)
			return "AUTO_INCREMENT";
		return "";
	}
	
	/**
	 * 设置级联删除
	 * @param clzz
	 * @return
	 */
	private static String isCascadeDel(Class<?> clzz) {
		if(PojoManage.cascadeDelete(clzz))
			return " ON DELETE CASCADE";
		return "";
	}
	
	/**
	 * 设置级联更新
	 * @param clzz
	 * @return
	 */
	private static String isCascadeUpd(Class<?> clzz) {
		if(PojoManage.cascadeUpdate(clzz))
			return " ON UPDATE CASCADE";
		return "";
	}
	
	/**
	 * 是否允许为NULL
	 * @param field
	 * @return
	 */
	private static String allownull(Field field) {
		if(PojoManage.allownull(field)) {
			return " DEFAULT NULL ";
		}
		return " NOT NULL ";
	}
	
}

