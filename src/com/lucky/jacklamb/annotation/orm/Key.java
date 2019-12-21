package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �����ʶ
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Key {
	
	/**
	 * ����ֶ���
	 * @return
	 */
	String value() default "";
	
	/**
	 * ���ý���ʱ���ֶγ���,Ĭ��35
	 * @return
	 */
	int length() default 35;
	
	/**
	 * ����ʱ�Ƿ�������ֶ�ΪNULL��Ĭ��true
	 * @return
	 */
	boolean allownull() default true;
	
	/**
	 * �����ָ�������Ӧ��ʵ����Class
	 * @return
	 */
	Class<?> pojo();
}
