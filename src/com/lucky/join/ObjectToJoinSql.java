package com.lucky.join;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import com.lucky.annotation.Id;
import com.lucky.annotation.Key;
import com.lucky.sqldao.PojoManage;

public class ObjectToJoinSql {

	private String join;
	private Object[] obj;

	public ObjectToJoinSql(String join, Object... obj) {
		this.join = join;
		this.obj = obj;
	}

	/**
	 * 得到JOIN ON部分的SQl
	 * 
	 * @return
	 */
	private String onFragment() {
		String sql = "";
		for (int i = 0; i < obj.length; i++) {
			Class<?> clzz_i = obj[i].getClass();
			Field[] field_i = obj[i].getClass().getDeclaredFields();
			if (i == 0) {
				sql += PojoManage.getTable(clzz_i);
			} else {
				sql += " " + join + " " + PojoManage.getTable(clzz_i) + " ON ";
				for (int j = i - 1; j >= 0; j--) {
					Field[] field_j = obj[j].getClass().getDeclaredFields();
					for (int ii = 0; ii < field_i.length; ii++) {
						for (int jj = 0; jj < field_j.length; jj++) {
							if (field_i[ii].getName().equals(field_j[jj].getName())) {
								sql += PojoManage.getTable(clzz_i) + "." + field_i[ii].getName() + "="
										+ PojoManage.getTable(obj[j].getClass()) + "." + field_j[jj].getName();
							}
						}
					}
				}

			}
		}
		return sql;
	}

	/**
	 * 得到AND 部分的SQL
	 * 
	 * @return
	 */
	private String andFragment() {
		String sql = "";
		int p = 0;
		for (int i = 0; i < obj.length; i++) {
			Class<?> clzz = obj[i].getClass();
			Field[] fields = clzz.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				fields[j].setAccessible(true);
				Object fk;
				try {
					fk = fields[j].get(obj[i]);
					if (fk != null) {
						if (p == 0) {
							sql += " WHERE " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j])
									+ "=?";
							p++;
						} else {
							sql += " AND " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j])
									+ "=?";

						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sql;
	}

	public Object[] getJoinObject() {
		List<Object> list = new ArrayList<>();
		for (int i = 0; i < obj.length; i++) {
			Field[] fields = obj[i].getClass().getDeclaredFields();
			try {
				for (int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					Object object = fields[j].get(obj[i]);
					if (object != null) {
						list.add(object);
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list.toArray();
	}

	/**
	 * 得到连接操作的SQL
	 * 
	 * @return
	 */
	public String getJoinSql() {
		return "SELECT * FROM " + onFragment() + andFragment();
	}

	private String listOnFragment() {
		String onsql = "";
		List<Class<?>> inputs = new ArrayList<>();
		Stream.of(obj).map(obj -> obj.getClass()).forEach(inputs::add);
		boolean fk = true;
		for (Class<?> pojoClass : inputs) {
			if (fk && !PojoManage.getKeyFields(pojoClass, true).isEmpty()) {
				fk = false;
				String keysql = objectOnFragment(pojoClass, inputs,PojoManage.getTable(pojoClass));
				onsql +=keysql;
			} else {
				String keysql = objectOnFragment(pojoClass, inputs,"");
				onsql += keysql;
			}
		}
		return onsql;
	}

	private String objectOnFragment(Class<?> pojoClass, List<Class<?>> inputs, String onsql) {
		Map<Field, Class<?>> keyFieldMap = PojoManage.getKeyFieldMap(pojoClass);
		if (keyFieldMap.isEmpty())
			return "";
		for (Entry<Field, Class<?>> entry : keyFieldMap.entrySet()) {
			onsql += " " + join;
			String keyTable = PojoManage.getTable(entry.getValue());// 外键对应的表名
			String primaryTable=PojoManage.getTable(pojoClass);//主表表明
			// 连接条件
			String condition = PojoManage.getTable(pojoClass) + "." + PojoManage.getTableField(entry.getKey()) + "="
					+ keyTable + "." + PojoManage.getIdString(entry.getValue());
			if(onsql.contains(primaryTable))
				onsql += " ON " + keyTable + " " + condition;
			else
				onsql += " ON " + primaryTable + " " + condition;
			String keysql= objectOnFragment(entry.getValue(), inputs, "");
			System.out.println("onsql: "+onsql+"\nkeysql: "+keysql);
			System.out.println(pojoClass.getSimpleName()+"-->"+entry.getValue().getSimpleName()+" :onsql包含keysql？"+onsql.contains(keysql)+"  "+"keysql包含nosql？"+keysql.contains(onsql)+"\n");
			onsql+=keysql;
		}
		return onsql;
	}

	public static void main(String[] args) {
		ObjectToJoinSql ojs = new ObjectToJoinSql("JOIN", new Stort(), new Author(), new Book());
		List<Class<?>> input = new ArrayList<>();
		input.add(Book.class);
		input.add(Author.class);
		input.add(Stort.class);
		System.out.println(ojs.listOnFragment());
	}

}

class Book {
	@Id
	private Integer bid;

	@Key(pojo = Author.class)
	private Integer autid;

	@Key(pojo = Stort.class)
	private Integer stid;

}

class Author {
	@Id
	private Integer autid;

	@Key(pojo = Stort.class)
	private Integer ppy;
}

class Stort {
	@Id
	private Integer stid;
}