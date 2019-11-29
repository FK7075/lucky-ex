package com.lucky.jacklamb.sqlcore.databaseimpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.lucky.jacklamb.sqlcore.PojoManage;

public class FieldAndValue {

	private String idField;

	private Object idValue;

	private Map<String, Object> fieldNameAndValue;
	
	private Object pojo;

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public Object getIdValue() {
		return idValue;
	}

	public void setIdValue(Object idValue) {
		this.idValue = idValue;
	}

	public Map<String, Object> getFieldNameAndValue() {
		return fieldNameAndValue;
	}

	public void setFieldNameAndValue(Map<String, Object> fieldNameAndValue) {
		this.fieldNameAndValue = fieldNameAndValue;
	}

	public FieldAndValue(Object pojo) {
		try {
			this.pojo=pojo;
			setIDField(pojo);
			setNotNullFields(pojo);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("���Ϸ������쳣��",e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("�Ƿ������쳣",e);
		}
		
	}
	
	public boolean containsField(String field) {
		return fieldNameAndValue.containsKey(field);
	}
	
	public boolean containsFields(String...fields) {
		for(String str:fields) {
			if(!containsField(str))
				throw new RuntimeException("�����"+pojo.getClass().getName()+"����"+pojo.toString()+"�ķǿ�����ӳ���в�����\""+str+"\",�޷���ɸ��²���");
		}
		return true;
	}

	public void setIDField(Object pojo) throws IllegalArgumentException, IllegalAccessException {
		Class<?> pojoClass = pojo.getClass();
		Field id = PojoManage.getIdField(pojoClass);
		id.setAccessible(true);
		this.idField = PojoManage.getTableField(id);
		this.idValue = id.get(pojo);
	}

	public void setNotNullFields(Object pojo) throws IllegalArgumentException, IllegalAccessException {
		fieldNameAndValue = new HashMap<>();
		Class<?> pojoClass = pojo.getClass();
		Field[] fields = pojoClass.getDeclaredFields();
		Object fieldValue;
		for (Field field : fields) {
			field.setAccessible(true);
			fieldValue = field.get(pojo);
			if (fieldValue != null)
				fieldNameAndValue.put(PojoManage.getTableField(field), fieldValue);
		}
	}
}