package com.lucky.jacklamb.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.lucky.jacklamb.enums.PrimaryType;
import com.lucky.jacklamb.sqldao.PojoManage;

public class BatchInsert {
	
	private String insertSql;
	
	private Object[] insertObject;

	public String getInsertSql() {
		return insertSql;
	}

	public Object[] getInsertObject() {
		return insertObject;
	}
	
	public <T> BatchInsert(List<T> list) {
		if(!list.isEmpty()) {
			Class<?> pojoClass=list.get(0).getClass();
			insertSql=createInsertSql(pojoClass,list.size());
			insertObject=createInsertObject(list);
		}
	}
	
	public static String createInsertSql(Class<?> clzz,int size) {
		Field[] fields=clzz.getDeclaredFields();
		String prefix="INSERT INTO "+PojoManage.getTable(clzz);
		String suffix=" VALUES ";
		if(PojoManage.getIdType(clzz)==PrimaryType.AUTO_INT) {
			List<Field> list=new ArrayList<>();
			String id=PojoManage.getIdString(clzz);
			Stream.of(fields).filter(field->!id.equals(PojoManage.getTableField(field))
					&&field.getType().getClassLoader()==null
					&&!(field.getType()).isAssignableFrom(List.class)).forEach(list::add);
			String fk="";
			for(int i=0;i<list.size();i++) {
				if(i==0) {
					prefix+="("+PojoManage.getTableField(list.get(i))+",";
					fk+="(?,";
				}else if(i==list.size()-1) {
					prefix+=PojoManage.getTableField(list.get(i))+")";
					fk+="?)";
				}else {
					prefix+=PojoManage.getTableField(list.get(i))+",";
					fk+="?,";
				}
			}
			for(int j=0;j<size;j++) {
				if(j==size-1) {
					suffix+=fk;
				}else {
					suffix+=fk+",";
				}
			}
		}else {
			List<Field> list=new ArrayList<>();
			Stream.of(fields).filter(field->field.getType().getClassLoader()==null
					&&!(field.getType()).isAssignableFrom(List.class)).forEach(list::add);
			String fk="";
			for(int i=0;i<list.size();i++) {
				if(i==0) {
					prefix+="("+PojoManage.getTableField(list.get(i))+",";
					fk+="(?,";
				}else if(i==list.size()-1) {
					prefix+=PojoManage.getTableField(list.get(i))+")";
					fk+="?)";
				}else {
					prefix+=PojoManage.getTableField(list.get(i))+",";
					fk+="?,";
				}
			}
			for(int j=0;j<size;j++) {
				if(j==size-1) {
					suffix+=fk;
				}else {
					suffix+=fk+",";
				}
			}
		}
		return prefix+suffix;
	}
	
	private <T> Object[] createInsertObject(List<T> list) {
		List<Object> po=new ArrayList<>();
		for(T t:list) {
			Class<?> clzz=t.getClass();
			String id=PojoManage.getIdString(clzz);
			Field[] fields=clzz.getDeclaredFields();
			if(PojoManage.getIdType(clzz)==PrimaryType.AUTO_INT) {
				for(Field fie:fields) {
					if(fie.getType().getClassLoader()==null
							&&!(fie.getType()).isAssignableFrom(List.class)
							&&!id.equals(PojoManage.getTableField(fie))) {
						fie.setAccessible(true);
						try {
							Object object=fie.get(t);
							po.add(object);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}else {
				for(Field fie:fields) {
					if(fie.getType().getClassLoader()==null&&!(fie.getType()).isAssignableFrom(List.class)) {
						fie.setAccessible(true);
						try {
							Object object=fie.get(t);
							po.add(object);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return po.toArray();
	}
	
}
