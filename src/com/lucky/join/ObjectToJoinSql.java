package com.lucky.join;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
	 * @return
	 */
	private String onFragment() {
		String sql = "";
		for(int i=0;i<obj.length;i++) {
			Class<?> clzz_i=obj[i].getClass();
			Field[] field_i=obj[i].getClass().getDeclaredFields();
			if(i==0) {
				sql+=PojoManage.getTable(clzz_i);
			}else {
				sql+=" "+join+" "+PojoManage.getTable(clzz_i)+" ON ";
				for(int j=i-1;j>=0;j--) {
					Field[] field_j=obj[j].getClass().getDeclaredFields();
					for(int ii=0;ii<field_i.length;ii++) {
						for(int jj=0;jj<field_j.length;jj++) {
							if(field_i[ii].getName().equals(field_j[jj].getName())) {
									sql+=PojoManage.getTable(clzz_i)+"."+field_i[ii].getName()+
										"="+PojoManage.getTable(obj[j].getClass())+"."+field_j[jj].getName();
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
	 * @return
	 */
	private String andFragment() {
		String sql = "";
		int p=0;
		for (int i = 0; i < obj.length; i++) {
			Class<?> clzz = obj[i].getClass();
			Field[] fields = clzz.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				fields[j].setAccessible(true);
				Object fk;
				try {
					fk = fields[j].get(obj[i]);
					if (fk != null) {
						if(p==0) {
							sql += " WHERE " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j]) + "=?";
							p++;
						}else {
							sql += " AND " + PojoManage.getTable(clzz) + "." + PojoManage.getTableField(fields[j]) + "=?";
							
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
					if(object!=null) {
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
	 * @return
	 */
	public String getJoinSql() {
		return "SELECT * FROM "+onFragment() + andFragment();
	}
	

}
