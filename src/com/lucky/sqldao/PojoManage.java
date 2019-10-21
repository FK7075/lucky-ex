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
			idField.setAccessible(true);
			String uuid=UUID.randomUUID().toString().replaceAll("-", "");
			fieldname.add(getTableField(idField));
			fieldvalue.add(uuid);
			try {
				idField.set(pojo, uuid);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if("".equals(coumn.value()))
				return field.getName().toLowerCase();
			return coumn.value().toLowerCase();
		}else if(field.isAnnotationPresent(Id.class)) {
			Id id=field.getAnnotation(Id.class);
			if("".equals(id.value()))
				return field.getName().toLowerCase();
			return id.value().toLowerCase();
		}else if(field.isAnnotationPresent(Key.class)) {
			Key key=field.getAnnotation(Key.class);
			if("".equals(key.value()))
				return field.getName().toLowerCase();
			return key.value().toLowerCase();
		}else {
			return field.getName().toLowerCase();
		}
	}
	
	/**
	 * �õ����ֶ��Ƿ����Ϊnull������
	 * @param field
	 * @return
	 */
	public static boolean allownull(Field field) {
		if(field.isAnnotationPresent(Column.class)) {
			return field.getAnnotation(Column.class).allownull();
		}else if(field.isAnnotationPresent(Key.class)) {
			return field.getAnnotation(Key.class).allownull();
		}else {
			return true;
		}
	}
	
	/**
	 * �õ����Եĳ�������
	 * @param field
	 * @return
	 */
	public static int getLength(Field field) {
		if(field.isAnnotationPresent(Id.class)) {
			return field.getAnnotation(Id.class).length();
		}else if(field.isAnnotationPresent(Key.class)) {
			return field.getAnnotation(Key.class).length();
		}else if(field.isAnnotationPresent(Column.class)) {
			return field.getAnnotation(Column.class).length();
		}else {
			return 35;
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
			if("".equals(table.value()))
				return pojoClass.getSimpleName().toLowerCase();
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
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.cascadeDelete();
		}
		return false;
	}
	
	/**
	 * �õ���ʵ���Ӧ��ļ����³���Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static boolean cascadeUpdate(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.cascadeUpdate();
		}
		return false;
	}
	
	/**
	 * �õ���ʵ�����ӳ��������
	 * @param pojoClass
	 * @return
	 */
	public static String getIdString(Class<?> pojoClass) {
		Field idField = getIdField(pojoClass);
		Id id = idField.getAnnotation(Id.class);
		if("".equals(id.value()))
			return idField.getName();
		return id.value();
	}
	

	/**
	 * �õ���ʵ���������ӳ���������������ɵ�Map
	 * @param pojoClass
	 * @return
	 */
	public static Map<Field,Class<?>> getKeyFieldMap(Class<?> pojoClass){
		Map<Field,Class<?>> keys=new HashMap<>();
		Field[] pojoFields=pojoClass.getDeclaredFields();
		for(Field field:pojoFields) {
			if(field.isAnnotationPresent(Key.class)) {
				Key key=field.getAnnotation(Key.class);
				keys.put(field, key.pojo());
			}
		}
		return keys;
	}
	
	/**
	 * �õ���ʵ���Ӧ��������Ϣ
	 * @param pojoClass
	 * @param iskey true(����������Լ���)/false(���������Ӧ�ĵ�ʵ��Class)
	 * @return
	 */
	public static List<?> getKeyFields(Class<?> pojoClass,boolean iskey){
		Map<Field,Class<?>> keyAdnField=getKeyFieldMap(pojoClass);
		List<Field> keys=new ArrayList<>();
		List<Class<?>> clzzs=new ArrayList<>();
		for(Entry<Field,Class<?>> entry:keyAdnField.entrySet()) {
			keys.add(entry.getKey());
			clzzs.add(entry.getValue());
		}
		if(iskey)
			return keys;
		else
			return clzzs;
	}
	
	/**
	 * �жϸ�ʵ���Ӧ�����������(����int����/UUID����/��ͨ����)
	 * @param pojoClass
	 * @return
	 */
	public static Type getIdType(Class<?> pojoClass) {
		Field idF=getIdField(pojoClass);
		Id id=idF.getAnnotation(Id.class);
		return id.type();
	}
	
	/**
	 * �õ�����������������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String primary(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.primary();
		}else {
			return "";
		}
	}
	
	/**
	 * �õ�������ͨ��������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] index(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.index();
		}else {
			return new String[0];
		}
	}
	
	/**
	 * �õ�����Ψһֵ��������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] unique(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.unique();
		}else {
			return new String[0];
		}
	}
	
	/**
	 * �õ�����ȫ����������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] fulltext(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.fulltext();
		}else {
			return new String[0];
		}
	}
}
