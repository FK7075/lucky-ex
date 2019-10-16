package com.lucky.sqldao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.lucky.annotation.Column;
import com.lucky.annotation.Id;
import com.lucky.annotation.Key;
import com.lucky.annotation.Table;
import com.lucky.enums.Type;

/**
 * ʵ���������
 * @author fk-7075
 *
 */
public class PojoManage {

	/**
	 * ����һ��Pojo��ʵ������һ����Ӧ��ClassInfo����
	 * @param pojo
	 * @return
	 */
	public static ClassInfo createClassInfo(Object pojo) {
		ClassInfo classInfo = new ClassInfo();
		Class<?> clzz = pojo.getClass();
		classInfo.setClzz(clzz);
		Field[] fields = clzz.getDeclaredFields();
		classInfo.setClassName(getTable(clzz));
		List<String> fieldname = new ArrayList<>();
		List<Object> fieldvalue = new ArrayList<>();
		Field idField=getIdField(clzz);
		if(getIdType(clzz)==Type.AUTO_UUID) {
			fieldname.add(getTableField(idField));
			fieldvalue.add(UUID.randomUUID().toString().replaceAll("_", ""));
		}
		for (Field field : fields) {
			try {
				if(field.equals(idField))
					continue;
				field.setAccessible(true);
				Object fieldobj = field.get(pojo);
				if (fieldobj != null) {
					fieldname.add(getTableField(field));
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
	
	/**
	 * �õ���ʵ�������Զ�Ӧ�����ݿ�ӳ��
	 * @param field
	 * @return
	 */
	public static String getTableField(Field field) {
		if(field.isAnnotationPresent(Column.class)) {
			Column coumn=field.getAnnotation(Column.class);
			return coumn.value().toLowerCase();
		}else if(field.isAnnotationPresent(Id.class)) {
			Id id=field.getAnnotation(Id.class);
			return id.value().toLowerCase();
		}else if(field.isAnnotationPresent(Key.class)) {
			Key key=field.getAnnotation(Key.class);
			return key.value().toLowerCase();
		}else {
			return field.getName().toLowerCase();
		}
	}
	
	/**
	 * �õ���ʵ�����Id����
	 * @param pojoClass
	 * @return
	 */
	public static Field getIdField(Class<?> pojoClass) {
		Field[] pojoFields=pojoClass.getDeclaredFields();
		for(Field field:pojoFields) {
			if(field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * �õ���ʵ�����ӳ�����
	 * @param pojoClass
	 * @return
	 */
	public static String getTable(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.value().toLowerCase();
		}else {
			return pojoClass.getSimpleName().toLowerCase();
		}
	}
	
	/**
	 * �õ���ʵ���Ӧ��ļ���ɾ����Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static boolean cascadeDelete(Class<?> pojoClass) {
		Table table=pojoClass.getAnnotation(Table.class);
		return table.cascadeDelete();
	}
	
	/**
	 * �õ���ʵ���Ӧ��ļ����³���Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static boolean cascadeUpdate(Class<?> pojoClass) {
		Table table=pojoClass.getAnnotation(Table.class);
		return table.cascadeUpdate();
	}
	
	/**
	 * �õ���ʵ�����ӳ��������
	 * @param pojoClass
	 * @return
	 */
	public static String getIdString(Class<?> pojoClass) {
		Field idField = getIdField(pojoClass);
		Id id = idField.getAnnotation(Id.class);
		return id.value();
	}
	

	/**
	 * �õ���ʵ���������ӳ���������������ɵ�Map
	 * @param pojoClass
	 * @return
	 */
	public static Map<String,Class<?>> getKeyFieldMap(Class<?> pojoClass){
		Map<String,Class<?>> keys=new HashMap<>();
		Field[] pojoFields=pojoClass.getDeclaredFields();
		for(Field field:pojoFields) {
			if(field.isAnnotationPresent(Key.class)) {
				Key key=field.getAnnotation(Key.class);
				keys.put(key.value(), key.pojo());
			}
		}
		return keys;
	}
	
	/**
	 * �õ���ʵ���Ӧ��������Ϣ
	 * @param pojoClass
	 * @param iskey true(�������������)/false(���������Ӧ�ĵ�ʵ��Class)
	 * @return
	 */
	public static List<?> getKeyFields(Class<?> pojoClass,boolean iskey){
		Map<String,Class<?>> keyAdnField=getKeyFieldMap(pojoClass);
		List<String> keys=new ArrayList<>();
		List<Class<?>> clzzs=new ArrayList<>();
		for(Entry<String,Class<?>> entry:keyAdnField.entrySet()) {
			keys.add(entry.getKey());
			clzzs.add(entry.getValue());
		}
		if(iskey)
			return keys;
		else
			return clzzs;
	}
	
	/**
	 * �жϸ�ʵ���Ӧ�����������
	 * @param pojoClass
	 * @return
	 */
	public static Type getIdType(Class<?> pojoClass) {
		Field idF=getIdField(pojoClass);
		Id id=idF.getAnnotation(Id.class);
		return id.type();
	}
	
}
