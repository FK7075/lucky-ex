package com.lucky.jacklamb.ioc;

import java.lang.reflect.Field;

public class ComponentFactory {

	/**
	 * �������
	 * @param t ԭ�Ͷ���
	 * @return �����Ķ���
	 */
	@SuppressWarnings("unchecked")
	public <T> T copy(T t) {
		Class<?> clzz= t.getClass();
		Field[] allField = clzz.getDeclaredFields();
		try {
			Object copy=clzz.newInstance();
			for(Field field:allField) {
				field.setAccessible(true);
				field.set(copy, field.get(t));
			}
			return (T) copy;
		} catch (InstantiationException e) {
			throw new RuntimeException("InstantiationException", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("IllegalAccessException", e);
		}
	} 
}
