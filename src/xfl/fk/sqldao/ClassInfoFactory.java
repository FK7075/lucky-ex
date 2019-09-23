package xfl.fk.sqldao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.Column;
import xfl.fk.annotation.Lucky;

public class ClassInfoFactory {

	public static ClassInfo createClassInfo(Object pojo) {
		ClassInfo classInfo = new ClassInfo();
		Class<?> clzz = pojo.getClass();
		classInfo.setClzz(clzz);
		Field[] fields = clzz.getDeclaredFields();
		Lucky lucky=clzz.getAnnotation(Lucky.class);
		String table=lucky.table();
		if("".equals(table)) {
			classInfo.setClassName(clzz.getSimpleName().toLowerCase());
		}else {
			classInfo.setClassName(table.toLowerCase());
		}
		List<String> fieldname = new ArrayList<>();
		List<Object> fieldvalue = new ArrayList<>();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object fieldobj = field.get(pojo);
				if (fieldobj != null) {
					if (field.isAnnotationPresent(Column.class)) {
						Column column = field.getAnnotation(Column.class);
						fieldname.add(column.value().toLowerCase());
					} else {
						fieldname.add(field.getName().toLowerCase());
					}
					fieldvalue.add(fieldobj);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		classInfo.setNames(fieldname);
		classInfo.setValues(fieldvalue);
		return classInfo;
	}
	
	public static String getTableField(Field field) {
		if(field.isAnnotationPresent(Column.class)) {
			Column coumn=field.getAnnotation(Column.class);
			return coumn.value().toLowerCase();
		}else {
			return field.getName().toLowerCase();
		}
	}
	
}
