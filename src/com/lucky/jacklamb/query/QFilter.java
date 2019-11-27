package com.lucky.jacklamb.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.sqlcore.PojoManage;

/**
 * ���ò�ѯ������
 * @author DELL
 *
 */
public class QFilter {
	
	private Class<?> pojoClass;
	
	private List<String> hfields;
	
	private List<String> sfields;
	

	/**
	 * ���ò�ѯ������
	 * @param pojoClass
	 */
	public QFilter(Class<?> pojoClass) {
		this.pojoClass = pojoClass;
		hfields=new ArrayList<>();
		sfields=new ArrayList<>();
		Field[] fields=pojoClass.getDeclaredFields();
		for(Field field:fields) {
			this.hfields.add(PojoManage.getTableField(field));
		}
	}
	
	/**
	 * ���ط�����ʱʹ��
	 * @param column
	 * @return
	 */
	public QFilter hidden(String column) {
		if(!hfields.contains(column))
			throw new RuntimeException(pojoClass.getName()+"��û����\""+column+"\"�ֶ���ƥ������ԣ�");
		hfields.remove(column);
		return this;
	}
	
	/**
	 * ���÷�����ʱʹ��
	 * @param column
	 * @return
	 */
	public QFilter show(String column) {
		if(!hfields.contains(column))
			throw new RuntimeException(pojoClass.getName()+"��û����\""+column+"\"�ֶ���ƥ������ԣ�");
		sfields.add(column);
		return this;
	}
	
	public String lines() {
		StringBuilder sb=new StringBuilder();
		if(sfields.isEmpty()) {
			for(String col:hfields) {
				sb.append(col).append(",");
			}
			return sb.substring(0, sb.length()-1).toString();
		}else {
			for(String col:sfields) {
				sb.append(col).append(",");
			}
			return sb.substring(0, sb.length()-1).toString();
		}
	}
	
}
