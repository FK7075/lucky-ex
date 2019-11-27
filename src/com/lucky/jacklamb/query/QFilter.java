package com.lucky.jacklamb.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.sqlcore.PojoManage;

/**
 * 设置查询返回列
 * @author DELL
 *
 */
public class QFilter {
	
	private Class<?> pojoClass;
	
	private List<String> hfields;
	
	private List<String> sfields;
	

	/**
	 * 设置查询返回列
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
	 * 隐藏返回列时使用
	 * @param column
	 * @return
	 */
	public QFilter hidden(String column) {
		if(!hfields.contains(column))
			throw new RuntimeException(pojoClass.getName()+"中没有与\""+column+"\"字段所匹配的属性！");
		hfields.remove(column);
		return this;
	}
	
	/**
	 * 设置返回列时使用
	 * @param column
	 * @return
	 */
	public QFilter show(String column) {
		if(!hfields.contains(column))
			throw new RuntimeException(pojoClass.getName()+"中没有与\""+column+"\"字段所匹配的属性！");
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
