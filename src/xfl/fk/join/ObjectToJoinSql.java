package xfl.fk.join;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.Lucky;

public class ObjectToJoinSql {

	private String join;
	private Object[] obj;

	public ObjectToJoinSql(String join, Object... obj) {
		this.join = join;
		this.obj = obj;
	}

	private String onFragment() {
		String sql = "";
		for(int i=0;i<obj.length;i++) {
			Class<?> clzz_i=obj[i].getClass();
			Field[] field_i=obj[i].getClass().getDeclaredFields();
			if(i==0) {
				sql+=getTableName(clzz_i);
			}else {
				sql+=" "+join+" "+getTableName(clzz_i)+" ON ";
				for(int j=i-1;j>=0;j--) {
					Field[] field_j=obj[j].getClass().getDeclaredFields();
					for(int ii=0;ii<field_i.length;ii++) {
						for(int jj=0;jj<field_j.length;jj++) {
							if(field_i[ii].getName().equals(field_j[jj].getName())) {
									sql+=getTableName(clzz_i)+"."+field_i[ii].getName()+
										"="+getTableName(obj[j].getClass())+"."+field_j[jj].getName();
							}
						}
					}
				}
				
			}
		}
		return sql;
	}

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
							sql += " WHERE " + getTableName(clzz) + "." + fields[j].getName() + "=?";
							p++;
						}else {
							sql += " AND " + getTableName(clzz) + "." + fields[j].getName() + "=?";
							
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

	public String getJoinSql() {
		return "SELECT * FROM "+onFragment() + andFragment();
	}
	
	private String getTableName(Class<?> clzz) {
		if(clzz.isAnnotationPresent(Lucky.class)) {
			Lucky lucy=clzz.getAnnotation(Lucky.class);
			if("".equals(lucy.table())) {
				return clzz.getSimpleName().toLowerCase();
			}else {
				return lucy.table().toLowerCase();
			}
		}else {
			return clzz.getSimpleName().toLowerCase();
		}
	}



}
