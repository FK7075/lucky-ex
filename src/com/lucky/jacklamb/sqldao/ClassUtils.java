package com.lucky.jacklamb.sqldao;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.utils.TypeChange;

/**
 * 泛型参数类类信息的抓取与sql语句拼接类
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class ClassUtils {
	private ClassInfo classInfo = null;
	private List<String> names;
	private List<Object> values;
	private TypeChange tych;
	private LogInfo log;

	public ClassUtils() {
		tych = new TypeChange();
		names = new ArrayList<String>();
		values = new ArrayList<Object>();
		log = new LogInfo();
		
	}
	
	
	/**
	 * 返回一个包含预编译的sql语句和填充占位符的Object[]的对象SqlInfo
	 * @param cs
	 * ClassInfo对象
	 * @param operation
	 * 操作关键字
	 * @return SqlInfo
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, String operation) {
		String sql = null;
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		/// 根据属性与属性值拼接sql语句
		if ("SELECT".equalsIgnoreCase(operation)) {
			sql = "SELECT * FROM " + cs2.getClassName();
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if(i==0)
					sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
				else
					sql+= "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("DELETE".equalsIgnoreCase(operation)) {
			sql = "DELETE FROM " + cs2.getClassName();
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if(i==0)
					sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
				else
					sql += "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("INSERT".equalsIgnoreCase(operation)) {
			String sql2 = ") VALUES(";
			sql = "INSERT INTO " + cs2.getClassName() + "(";
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if (i == cs2.getNames().size() - 1) {
					sql = sql + cs2.getNames().get(i);
					sql2 = sql2 + "?)";
				} else {
					sql = sql + cs2.getNames().get(i) + ",";
					sql2 = sql2 + "?,";
				}
			}
			sql = sql + sql2;
		}
		if ("UPDATE".equalsIgnoreCase(operation)) {
			IDAndLocation id_loca=new IDAndLocation(cs2);
			//////如果ID的位置不再最后,则从ID的位置开始，依次将之后的元素向前移动一位，最后将ID放到最后
			if(id_loca.getLocation()!=valueY.length-1) {
				for(int i=id_loca.getLocation();i<valueY.length-1;i++) {
					valueY[i]=valueY[i+1];
				}
				valueY[valueY.length-1]=id_loca.getId();
			}
			String sql2 = " WHERE " + PojoManage.getIdString(cs2.getClzz()) + "=?";
			sql = "UPDATE " + cs2.getClassName() + " SET ";
			if( cs2.getNames().size()==(id_loca.getLocation()+1)) {
				for (int i = 0; i < cs2.getNames().size()-1; i++) {
					if ((i != cs2.getNames().size() - 2))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}else {
				for (int i = 0; i < cs2.getNames().size(); i++) {
					if ((i != cs2.getNames().size() - 1)&&(i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else if((i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}
			sql = sql + sql2;
		}
		sqlInfo.setSql(sql);
		sqlInfo.setObj(valueY);
		return sqlInfo;
	}

	/**
	 * 返回分页查询的ClassInfo对象
	 * @param cs
	 * 过滤后的ClassInfo对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页记录数
	 * @return 预编译语句和填充对象数组的包装类
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, int index, int size) {
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName();
		String sql2 = " LIMIT " + index + "," + size;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			if(i==0)
				sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
			else
				sql += "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * 返回排序查询的ClassInfo对象
	 * @param cs
	 * 过滤后的ClassInfo对象
	 * @param property
	 * 要排序的属性名
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public SqlInfo getSqlInfo(ClassInfo cs2, String property, int r) {
		String ronk = null;
		if (r == 0)
			ronk = "ASC";
		if (r == 1)
			ronk = "DESC";
		SqlInfo sqlInfo = new SqlInfo();
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName();
		String sql2 = " ORDER BY " + property + " " + ronk;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			if(i==0)
				sql+=" WHERE "+ cs2.getNames().get(i) + "=? ";
			else
				sql += "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * 返回简单模糊查询的ClassInfo对象
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return 预编译语句和填充对象数组的包装类
	 */
	public SqlInfo getSqlInfo(Class<?> c, String property, String info) {
		SqlInfo sqlInfo = new SqlInfo();
		sqlInfo.setSql("SELECT * FROM " + PojoManage.getTable(c) + " WHERE " + property + " LIKE ?");
		Object[] obj = { info };
		sqlInfo.setObj(obj);
		return sqlInfo;
	}

}
