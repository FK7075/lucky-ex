package com.lucky.jacklamb.annotation.orm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ȷ������������ֶε�ӳ���ϵ
 * 
 * @author fk-7075
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
	
	/**
	 * ���Զ�Ӧ���ݿ��е��ֶ���
	 * @return
	 */
	String value() default "";
	
	/**
	 * ����ʱ���ֶεĳ���,Ĭ��35
	 * @return
	 */
	int length() default 35;
	
	/**
	 * ����ʱ�Ƿ�������ֶ�ΪNULL��Ĭ��true
	 * @return
	 */
	boolean allownull() default true;
}
